package org.pl.parser.eval

import org.pl.lexer.token.AssignToken
import org.pl.lexer.token.NameToken
import org.pl.parser.IParser
import org.pl.parser.ast.INode
import org.pl.parser.ast.VarAssignNode
import org.pl.parser.exception.EvalException

/**
 * Evaluates the var assign semantics:
 *
 * <var-assign> ::= <name> '=' <expr>
 *
 */
class VarAssignEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        if (NameToken::class != parser.token::class) {
            throw EvalException("Variable identifier expected, but got ${parser.token}")
        }
        val id = (parser.tokenAndAdvance as NameToken).getValue()

        if (AssignToken::class != parser.token::class) {
            throw EvalException("Variable requires '=' to assign a new value")
        }
        parser.advanceCursor()
        return VarAssignNode(id, ExprEval(parser).eval())
    }
}