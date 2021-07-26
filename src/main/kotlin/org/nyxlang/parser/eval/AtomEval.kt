package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.*
import org.nyxlang.lexer.token.bracket.LeftBracketToken
import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.bracket.RightParenthesisToken
import org.nyxlang.lexer.token.keyword.AsyncKeywordToken
import org.nyxlang.lexer.token.keyword.FunKeywordToken
import org.nyxlang.lexer.token.keyword.IfKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.*
import org.nyxlang.parser.eval.expression.ArrayExprEval
import org.nyxlang.parser.eval.expression.ExprEval
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates atoms with the following semantic:
 *
 * <atom> ::= <bool> | <number> | <string> | <name>
 *          | '(' <expr> ')'
 *          | <arr-expr>
 *          | <if-expr>
 *          | <fun-declaration>
 *          | <coroutine>
 *          | <block>
 *          | <empty>
 *
 */
class AtomEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        return when (parser.token::class) {

            BoolToken::class -> BoolNode((parser.tokenAndAdvance as BoolToken).value)
            NumberToken::class -> NumberNode((parser.tokenAndAdvance as NumberToken).value)
            StringToken::class -> StringNode((parser.tokenAndAdvance as StringToken).value)
            NameToken::class -> VarAccessNode((parser.tokenAndAdvance as NameToken).value)

            LeftBracketToken::class -> ArrayExprEval(parser).eval()
            IfKeywordToken::class -> IfEval(parser).eval()
            FunKeywordToken::class -> FunDeclarationEval(parser).eval()
            AsyncKeywordToken::class -> parser.advance { AsyncNode(StatementEval(parser).eval()) }
            LeftCurlyBraceToken::class -> BlockEval(parser).eval()

            LeftParenthesisToken::class -> parser.advance {
                parser.skipNewLines()
                val result = ExprEval(parser).eval()
                parser.skipNewLines()
                if (RightParenthesisToken::class != parser.token::class) throw EvalException("Closing parenthesis ')' missing and got '${parser.token}'")
                parser.advanceCursor()
                result
            }

            else -> EmptyNode()
        }
    }
}
