package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.bracket.LeftBracketToken
import org.nyxlang.lexer.token.bracket.RightBracketToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.VarAssignNode
import org.nyxlang.parser.eval.expression.ExprEval
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the assignment semantics:
 *
 * <assignment> ::= <name> (<index-suffix>)? '=' <expr>
 *
 */
class AssignmentEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        if (NameToken::class != parser.token::class) throw EvalException("Identifier expected, but got ${parser.token}")
        val id = (parser.tokenAndAdvance as NameToken).value
        var indexAccess: INode? = null
        val expr = ExprEval(parser)

        if (LeftBracketToken::class == parser.token::class) {
            parser.skipNewLines()
            indexAccess = expr.eval()
            parser.skipNewLines()
            if (RightBracketToken::class != parser.token::class) throw EvalException("Index access requires closing bracket")
            parser.advanceCursor()
        }

        if (AssignToken::class == parser.token::class) {
            parser.skipNewLines()
            parser.advanceCursor()
            return VarAssignNode(id, ExprEval(parser).eval())
        }
        return expr.eval()
    }
}