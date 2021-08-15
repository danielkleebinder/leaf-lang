package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.*
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.StatementListNode

/**
 * Interprets a list of statements.
 */
class StatementListVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val statementListNode = node as StatementListNode
        var statementResult: IRuntimeResult = emptyResult()
        for (statement in statementListNode.statements) {
            statementResult = interpreter.interpret(statement)
            if (statementResult is BreakRuntimeResult) return statementResult
            if (statementResult is ContinueRuntimeResult) return statementResult
            if (statementResult is ReturnRuntimeResult) return statementResult
        }
        return statementResult
    }
}