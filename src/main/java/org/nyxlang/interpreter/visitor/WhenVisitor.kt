package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.WhenNode

/**
 * Interprets the when logic.
 */
class WhenVisitor : IVisitor {
    override fun matches(node: INode) = WhenNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val whenNode = node as WhenNode
        val argument = interpreter.evalNode(whenNode.arg).data

        for (whenCase in whenNode.cases) {
            val match = interpreter.evalNode(whenCase.matches).data
            if (argument == match) {
                return interpreter.evalNode(whenCase.body)
            }
        }
        if (whenNode.elseCase != null) return interpreter.evalNode(whenNode.elseCase)
        return emptyResult()
    }
}