package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.WhenNode

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