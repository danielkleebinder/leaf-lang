package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.VarAssignNode
import org.nyxlang.parser.exception.EvalException

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