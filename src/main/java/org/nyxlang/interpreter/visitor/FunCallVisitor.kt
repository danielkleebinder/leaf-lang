package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.ReturnRuntimeResult
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.interpreter.withDynamicScope
import org.nyxlang.parser.ast.FunCallNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets a function call node.
 */
class FunCallVisitor : IVisitor {
    override fun matches(node: INode) = FunCallNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val funCallNode = node as FunCallNode
        val funName = funCallNode.name
        val spec = funCallNode.spec
        val args = funCallNode.args

        // Do we even have access to this function in this scope?
        if (spec == null) throw VisitorException("Function with name \"$funName\" not defined in this scope")

        // Execute the arguments first before creating a new activation record,
        // otherwise, I would already execute the arguments in the context of the
        // function itself.
        val actualArgs = args.map { interpreter.interpret(it).data }
        var result: IRuntimeResult = emptyResult()

        // Push a new activation record onto the stack and assign the variables
        interpreter.withDynamicScope(funName) { activationRecord ->
            spec.params.zip(actualArgs).forEach {
                activationRecord.define(it.first.name, it.second)
            }
            activationRecord.staticLink = spec.staticScope

            if (false == interpreter.interpret(spec.requires).data) {
                throw VisitorException("Requires expression of function \"$funName\" failed")
            }

            result = interpreter.interpret(spec.body)

            if (result is ReturnRuntimeResult) {
                result = if (result.hasData()) dataResult(result.data!!) else emptyResult()
            }

            if (result.hasData()) {
                activationRecord.define("_", result.data)
                if (false == interpreter.interpret(spec.ensures).data) {
                    throw VisitorException("Ensures expression of function \"$funName\" failed")
                }
            }
        }
        return result
    }
}