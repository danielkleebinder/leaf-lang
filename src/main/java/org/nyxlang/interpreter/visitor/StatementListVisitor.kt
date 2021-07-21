package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.*
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.StatementListNode

/**
 * Interprets a list of statements.
 */
class StatementListVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val statementListNode = node as StatementListNode
        val result = listResult()
        for (statement in statementListNode.statements) {
            val statementResult = interpreter.interpret(statement)
            if (statementResult is BreakRuntimeResult) return statementResult
            if (statementResult is ContinueRuntimeResult) return statementResult
            if (statementResult is ReturnRuntimeResult) return statementResult
            result.data.add(statementResult)
        }
        return result
    }
}