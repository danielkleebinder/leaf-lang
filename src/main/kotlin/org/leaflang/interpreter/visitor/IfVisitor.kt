package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.parser.ast.IfNode
import org.leaflang.parser.ast.INode

/**
 * Interprets the conditional logic.
 */
class IfVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val ifNode = node as IfNode
        for (ifCase in ifNode.cases) {
            val conditionResult = interpreter.interpret(ifCase.condition).data
            if (conditionResult?.value == true) {
                return interpreter.interpret(ifCase.body)
            }
        }
        if (ifNode.elseCase != null) return interpreter.interpret(ifNode.elseCase)
        return emptyResult()
    }
}