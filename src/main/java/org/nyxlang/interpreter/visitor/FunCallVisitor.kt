package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.withDynamicScope
import org.nyxlang.parser.ast.FunCallNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets a function call node.
 */
class FunCallVisitor : IVisitor {
    override fun matches(node: INode) = FunCallNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): Any? {
        val funCallNode = node as FunCallNode
        val funName = funCallNode.name
        val spec = funCallNode.spec
        val args = funCallNode.args

        // Do we even have access to this function in this scope?
        if (spec == null) throw VisitorException("Function with name \"$funName\" not defined in this scope")

        // Execute the arguments first before creating a new activation record,
        // otherwise, I would already execute the arguments in the context of the
        // function itself.
        val actualArgs = args.map { interpreter.evalNode(it) }
        var result: Any? = null

        // Push a new activation record onto the stack and assign the variables
        interpreter.withDynamicScope(funName) { activationRecord ->
            spec.params.zip(actualArgs).forEach {
                activationRecord.define(it.first.name, it.second)
            }
            activationRecord.staticLink = spec.staticScope

            if (false == interpreter.evalNode(spec.requires)) {
                throw VisitorException("Requires expression of function \"$funName\" failed")
            }

            result = interpreter.evalNode(spec.body)

            if (spec.returns != null) {
                activationRecord.define("_", result)
                if (false == interpreter.evalNode(spec.ensures)) {
                    throw VisitorException("Ensures expression of function \"$funName\" failed")
                }
            }
        }
        return result
    }
}