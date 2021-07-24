package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.parser.ast.IfNode
import org.nyxlang.parser.ast.INode

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