package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.LoopNode

/**
 * Interprets the loop logic.
 */
class LoopVisitor : IVisitor {

    override fun matches(node: INode) = LoopNode::class == node::class

    override fun visit(interpreter: IInterpreter, node: INode): Any {
        val loopNode = node as LoopNode
        val result = arrayListOf<Any>()

        // Do the initialization of the loop
        if (loopNode.init != null) interpreter.evalNode(loopNode.init)

        println(loopNode)

        while (loopNode.condition == null || interpreter.evalNode(loopNode.condition) == true) {
            val bodyResult = interpreter.evalNode(loopNode.body)

            // I want to prevent very deep lists from occurring. This check prevents that
            // something like [true] becomes [[[true]]]
            if (bodyResult is Collection<*>) {
                result.addAll(bodyResult as Collection<Any>)
            } else if (bodyResult != null) {
                result.add(bodyResult)
            }

            // Do one step of the loops step expression
            interpreter.evalNode(loopNode.step)
        }
        return result
    }
}