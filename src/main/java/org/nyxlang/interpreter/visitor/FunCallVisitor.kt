package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.withActivationRecord
import org.nyxlang.parser.ast.FunCallNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets a function call node.
 */
class FunCallVisitor : IVisitor {
    override fun matches(node: INode) = FunCallNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode) {
        val funCallNode = node as FunCallNode
        val funName = funCallNode.name
        val spec = funCallNode.spec
        val args = funCallNode.args

        // Execute the arguments first before creating a new activation record,
        // otherwise, I would already execute the arguments in the context of the
        // function itself.
        val actualArgs = args.map { interpreter.evalNode(it) }

        // Push a new activation record onto the stack and assign the variables
        interpreter.withActivationRecord(funName) { activationRecord ->
            spec!!.params.zip(actualArgs).forEach {
                activationRecord.define(it.first.name, it.second)
            }

            if (false == interpreter.evalNode(spec.requires)) {
                throw VisitorException("Requires expression of function \"$funName\" failed")
            }

            val result = interpreter.evalNode(spec.body)

            if (spec.returns != null) {
                activationRecord.define("_", result)
                if (false == interpreter.evalNode(spec.ensures)) {
                    throw VisitorException("Ensures expression of function \"$funName\" failed")
                }
            }
        }
    }
}