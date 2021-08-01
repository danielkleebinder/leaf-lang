package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.AssignmentNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the assignment semantics:
 *
 * <assignment> ::= <var> ('=' <expr>)?
 *
 */
class AssignmentEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        if (NameToken::class != parser.token::class) throw EvalException("Identifier expected for assignment, but got ${parser.token}")

        val variable = VariableEval(parser).eval()

        if (AssignToken::class == parser.token::class) {
            parser.skipNewLines()
            parser.advanceCursor()

            val assignmentExpr = ExprEval(parser).eval()
            return AssignmentNode(variable, assignmentExpr)
        }
        return variable
    }
}