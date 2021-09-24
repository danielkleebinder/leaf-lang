package org.leaflang.interpreter.visitor

import org.leaflang.analyzer.symbol.ClosureSymbol
import org.leaflang.analyzer.symbol.NativeFunSymbol
import org.leaflang.error.ErrorCode
import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.memory.IActivationRecord
import org.leaflang.interpreter.memory.cell.ClosureMemoryCell
import org.leaflang.interpreter.memory.cell.IMemoryCell
import org.leaflang.interpreter.memory.cell.NativeFunMemoryCell
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.interpreter.result.ReturnRuntimeResult
import org.leaflang.interpreter.result.dataResult
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.interpreter.withDynamicScope
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.access.AccessCallNode
import org.leaflang.parser.ast.access.AccessFieldNode
import org.leaflang.parser.ast.access.AccessIndexNode
import org.leaflang.parser.ast.access.AccessNode

/**
 * Interprets the access node recursively.
 */
class AccessVisitor : IVisitor {

    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val accessNode = node as AccessNode
        val name = accessNode.name

        var value: IMemoryCell? = interpreter.activationRecord!![name]
        var previousValue: IMemoryCell? = null

        for (child in accessNode.children) {
            if (value == null) {
                interpreter.error(child, ErrorCode.INVALID_MEMORY_ACCESS, "Cannot perform operation \"$child\" because the parent did not return anything")
                break
            }
            val tmp = value
            value = when (child) {
                is AccessCallNode -> visitCallAccess(previousValue, value, child, interpreter)
                is AccessFieldNode -> visitFieldAccess(value, child, interpreter)
                is AccessIndexNode -> visitIndexAccess(value, child, interpreter)
                else -> {
                    // ASSERT: This error is never allowed to happen because it must be caught by either syntax or
                    //         static semantic analysis anyways.
                    interpreter.abort(child, ErrorCode.INVALID_MEMORY_ACCESS)
                    null
                }
            }
            previousValue = tmp
        }

        return if (value != null) dataResult(value) else emptyResult()
    }

    /**
     * Visits the field access node and returns the fields value.
     */
    private fun visitFieldAccess(current: IMemoryCell, node: AccessFieldNode, interpreter: IInterpreter): IMemoryCell? {
        val fieldName = node.name
        val result = current.members[fieldName]
        if (result == null) {
            interpreter.error(node, ErrorCode.INVALID_FIELD_ACCESS, "Member field with name \"${fieldName}\" not found on value \"$current\"")
        }
        return result
    }

    /**
     * Visits the index access node and returns the value at the index of the current value.
     */
    private fun visitIndexAccess(current: IMemoryCell, node: AccessIndexNode, interpreter: IInterpreter): IMemoryCell? {
        val indexExpr = node.indexExpr
        val index = interpreter.interpret(indexExpr).data
        return if (index == null) {
            interpreter.error(node, ErrorCode.INVALID_INDEX_ACCESS, "Not an index")
            null
        } else current.get(index)
    }

    /**
     * Visits the call access node and returns the interpreted result.
     */
    private fun visitCallAccess(previous: IMemoryCell?, current: IMemoryCell, node: AccessCallNode, interpreter: IInterpreter): IMemoryCell? {
        val args = node.args

        // Execute the arguments first before creating a new activation record,
        // otherwise, I would already execute the arguments in the context of the
        // function itself.
        val actualArgs = args.map { interpreter.interpret(it).data }
        var result: IRuntimeResult = emptyResult()

        val spec = current.value

        // We are dealing with a native function invocation. This is actually much
        // easier to deal with and faster.
        if (current is NativeFunMemoryCell && spec is NativeFunSymbol) {
            return spec.nativeFunction(actualArgs.toTypedArray())
        }

        // It is a user defined function that needs interpretation.
        if (current is ClosureMemoryCell && spec is ClosureSymbol) {
            val funName = spec.name

            // Static vs dynamic link algorithm as described by the new mexico
            // state university: https://www.cs.nmsu.edu/~rth/cs/cs471/f00/ARIs.html
            val callerDepth = current.executionContext.nestingLevel
            val declarerDepth = spec.nestingLevel
            val depthDifference = callerDepth - declarerDepth

            // The algorithm even works for negative depth difference (call up the
            // stack), because Kotlin won't execute negative ranges without "downTo".
            var staticLink: IActivationRecord? = current.executionContext
            for (i in 1..depthDifference) staticLink = staticLink?.staticLink

            // Push a new activation record onto the stack and assign the variables
            interpreter.withDynamicScope(funName) { activationRecord ->
                spec.params.zip(actualArgs).forEach {
                    activationRecord.define(it.first.name, it.second)
                }

                // Update the static link of the new activation record
                activationRecord.staticLink = staticLink
                activationRecord.nestingLevel = staticLink!!.nestingLevel + 1

                // Define the "object" context for this call access
                if (previous != null) activationRecord.define("object", previous)

                if (false == interpreter.interpret(spec.requires).data?.value) {
                    interpreter.abort(node, ErrorCode.REQUIRES_FAILED, "Invalid argument values provided for function \"$funName\"")
                    return@withDynamicScope
                }

                result = interpreter.interpret(spec.body)

                if (result is ReturnRuntimeResult) {
                    result = if (result.hasData()) dataResult(result.data!!) else emptyResult()
                }

                if (result.hasData()) {
                    activationRecord.define("_", result.data)
                    if (false == interpreter.interpret(spec.ensures).data?.value) {
                        interpreter.abort(node, ErrorCode.ENSURES_FAILED, "Function \"$funName\" failed due to 'ensures' clause")
                        return@withDynamicScope
                    }
                }
            }
        }
        return result.data
    }
}