package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.memory.IActivationRecord
import org.nyxlang.interpreter.result.*
import org.nyxlang.interpreter.withDynamicScope
import org.nyxlang.parser.ast.FunCallNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets a function call node.
 */
class FunCallVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val funCallNode = node as FunCallNode
        val funName = funCallNode.name
        val spec = funCallNode.spec
        val args = funCallNode.args
        val activationRecord = interpreter.activationRecord!!

        // Do we even have access to this function in this scope?
        if (spec == null) throw VisitorException("Function with name \"$funName\" not defined in this scope")

        // Execute the arguments first before creating a new activation record,
        // otherwise, I would already execute the arguments in the context of the
        // function itself.
        val actualArgs = args.map { interpreter.interpret(it).data }
        var result: IRuntimeResult = emptyResult()

        // Static vs dynamic link algorithm as described by the new mexico
        // state university: https://www.cs.nmsu.edu/~rth/cs/cs471/f00/ARIs.html
        val callerDepth = activationRecord.nestingLevel
        val declarerDepth = funCallNode.spec!!.nestingLevel
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

            if (false == interpreter.interpret(spec.requires).data) {
                throw VisitorException("Requires expression of function \"$funName\" failed")
            }

            result = interpreter.interpret(spec.body)

            if (result is ReturnRuntimeResult) {
                result = if (result.hasData()) dataResult(result.data!!) else emptyResult()
            }

            if (result.hasData()) {
                activationRecord.define("_", result.unpack())
                if (false == interpreter.interpret(spec.ensures).data) {
                    throw VisitorException("Ensures expression of function \"$funName\" failed")
                }
            }
        }
        return result
    }
}