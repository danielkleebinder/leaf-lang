package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.parser.ast.ConditionalNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets the conditional logic.
 */
class IfVisitor : IVisitor {
    override fun matches(node: INode) = ConditionalNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val ifNode = node as ConditionalNode
        for (ifCase in ifNode.cases) {
            val conditionResult = interpreter.evalNode(ifCase.condition).data
            if (conditionResult == true) {
                return interpreter.evalNode(ifCase.body)
            }
        }
        if (ifNode.elseCase != null) return interpreter.evalNode(ifNode.elseCase)
        return emptyResult()
    }
}