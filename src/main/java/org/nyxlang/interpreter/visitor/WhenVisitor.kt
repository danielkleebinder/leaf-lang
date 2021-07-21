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
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val whenNode = node as WhenNode
        val argument = interpreter.interpret(whenNode.arg).data

        for (whenCase in whenNode.cases) {
            val match = interpreter.interpret(whenCase.matches).data
            if (argument == match) {
                return interpreter.interpret(whenCase.body)
            }
        }
        if (whenNode.elseCase != null) return interpreter.interpret(whenNode.elseCase)
        return emptyResult()
    }
}