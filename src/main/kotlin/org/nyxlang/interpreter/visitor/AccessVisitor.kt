package org.nyxlang.interpreter.visitor

import org.nyxlang.analyzer.symbol.FunSymbol
import org.nyxlang.analyzer.symbol.NativeFunSymbol
import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.memory.IActivationRecord
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.ReturnRuntimeResult
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.interpreter.value.IValue
import org.nyxlang.interpreter.withDynamicScope
import org.nyxlang.parser.ast.*

/**
 * Interprets the access node recursively.
 */
class AccessVisitor : IVisitor {

    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val accessNode = node as AccessNode
        val name = accessNode.name

        var value: IValue? = interpreter.activationRecord!![name]

        for (child in accessNode.children) {
            if (value == null) throw VisitorException("Cannot perform operation \"$child\" because nothing was returned from the previous call")
            value = when (child::class) {
                AccessCallNode::class -> visitCallAccess(value, child as AccessCallNode, interpreter)
                AccessFieldNode::class -> visitFieldAccess(value, child as AccessFieldNode)
                AccessIndexNode::class -> visitIndexAccess(value, child as AccessIndexNode, interpreter)
                else -> throw VisitorException("Invalid child node \"$value\" for access")
            }
        }

        return if (value != null) dataResult(value) else emptyResult()
    }

    /**
     * Visits the field access node and returns the fields value.
     */
    private fun visitFieldAccess(current: IValue, node: AccessFieldNode): IValue {
        val fieldName = node.name
        return current.members[fieldName]
                ?: throw VisitorException("Member field with name \"${fieldName}\" not found on value \"$current\"")
    }

    /**
     * Visits the index access node and returns the value at the index of the current value.
     */
    private fun visitIndexAccess(current: IValue, node: AccessIndexNode, interpreter: IInterpreter): IValue {
        val indexExpr = node.indexExpr
        val index = interpreter.interpret(indexExpr).data
                ?: throw VisitorException("Not an index expression")
        return current.get(index)
    }

    /**
     * Visits the call access node and returns the interpreted result.
     */
    private fun visitCallAccess(current: IValue, node: AccessCallNode, interpreter: IInterpreter): IValue? {
        val activationRecord = interpreter.activationRecord!!
        val args = node.args

        // Execute the arguments first before creating a new activation record,
        // otherwise, I would already execute the arguments in the context of the
        // function itself.
        val actualArgs = args.map { interpreter.interpret(it).data }
        var result: IRuntimeResult = emptyResult()

        val spec = current.value

        // We are dealing with a native function invocation. This is actually much
        // easier to deal with and faster.
        if (spec is NativeFunSymbol) {
            return spec.nativeFunction(actualArgs.toTypedArray())
        }

        // It is a user defined function that needs interpretation.
        if (spec is FunSymbol) {
            val funName = spec.name

            // Static vs dynamic link algorithm as described by the new mexico
            // state university: https://www.cs.nmsu.edu/~rth/cs/cs471/f00/ARIs.html
            val callerDepth = activationRecord.nestingLevel
            val declarerDepth = spec.nestingLevel
            val depthDifference = callerDepth - declarerDepth

            // The algorithm even works for negative depth difference (call up the
            // stack), because Kotlin won't execute negative ranges without "downTo".
            var staticLink: IActivationRecord? = activationRecord
            for (i in 1..depthDifference) staticLink = staticLink?.staticLink

            // Push a new activation record onto the stack and assign the variables
            interpreter.withDynamicScope(funName) { activationRecord ->
                spec.params.zip(actualArgs).forEach {
                    activationRecord.define(it.first.name, it.second)
                }

                // Update the static link of the new activation record
                activationRecord.staticLink = staticLink
                activationRecord.nestingLevel = staticLink!!.nestingLevel + 1

                if (false == interpreter.interpret(spec.requires).data?.value) {
                    throw VisitorException("Requires expression of function \"$funName\" failed")
                }

                result = interpreter.interpret(spec.body)

                if (result is ReturnRuntimeResult) {
                    result = if (result.hasData()) dataResult(result.data!!) else emptyResult()
                }

                if (result.hasData()) {
                    activationRecord.define("_", result.data)
                    if (false == interpreter.interpret(spec.ensures).data?.value) {
                        throw VisitorException("Ensures expression of function \"$funName\" failed")
                    }
                }
            }
        }
        return result.data
    }
}