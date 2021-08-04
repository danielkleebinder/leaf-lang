package org.nyxlang.parser.eval

import org.nyxlang.error.ErrorCode
import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.AssignmentNode
import org.nyxlang.parser.ast.INode

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