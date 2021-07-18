package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.ConditionalNode

/**
 * Interprets the conditional logic.
 */
class IfVisitor : IVisitor {
    override fun matches(node: INode) = ConditionalNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): Any? {
        val ifNode = node as ConditionalNode
        for (ifCase in ifNode.cases) {
            val conditionResult = interpreter.evalNode(ifCase.condition)
            if (java.lang.Boolean.TRUE == conditionResult) {
                return interpreter.evalNode(ifCase.body)
            }
        }
        if (ifNode.elseCase != null) return interpreter.evalNode(ifNode.elseCase)
        return null
    }
}