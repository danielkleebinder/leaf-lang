package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.IfNode

/**
 * Interprets the conditional logic.
 */
class IfVisitor : IVisitor {
    override fun matches(node: INode) = IfNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): Any? {
        val ifNode = node as IfNode
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