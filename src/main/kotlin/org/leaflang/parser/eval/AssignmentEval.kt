package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.IParser
import org.leaflang.parser.ast.AssignmentNode
import org.leaflang.parser.ast.INode

/**
 * Evaluates the assignment semantics:
 *
 * <assignment> ::= <var> ('=' <expr>)?
 *
 */
class AssignmentEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        if (TokenType.IDENTIFIER != parser.token.kind) parser.flagError(ErrorCode.MISSING_IDENTIFIER)

        val variable = VariableEval(parser).eval()

        if (TokenType.ASSIGNMENT == parser.token.kind) {
            parser.advanceCursor()
            parser.skipNewLines()

            val assignmentExpr = ExprEval(parser).eval()
            return AssignmentNode(variable, assignmentExpr)
        }
        return variable
    }
}