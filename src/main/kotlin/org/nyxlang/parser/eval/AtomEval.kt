package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.BoolToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.NumberToken
import org.nyxlang.lexer.token.StringToken
import org.nyxlang.lexer.token.bracket.LeftBracketToken
import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.bracket.RightParenthesisToken
import org.nyxlang.lexer.token.keyword.AsyncKeywordToken
import org.nyxlang.lexer.token.keyword.FunKeywordToken
import org.nyxlang.lexer.token.keyword.IfKeywordToken
import org.nyxlang.lexer.token.keyword.NewKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.*
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates atoms with the following semantic:
 *
 * <atom> ::= <bool> | <number> | <string> | <name>
 *          | '(' <expr> ')'
 *          | <type-inst>
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

            NameToken::class -> AssignmentEval(parser).eval()
            LeftBracketToken::class -> ArrayExprEval(parser).eval()
            IfKeywordToken::class -> IfEval(parser).eval()
            FunKeywordToken::class -> FunDeclarationEval(parser).eval()
            NewKeywordToken::class -> TypeInstantiationEval(parser).eval()
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
