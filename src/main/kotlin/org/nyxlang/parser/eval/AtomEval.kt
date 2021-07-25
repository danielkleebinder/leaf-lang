package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.BoolToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.NumberToken
import org.nyxlang.lexer.token.StringToken
import org.nyxlang.lexer.token.bracket.LeftBracketToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.keyword.AsyncKeywordToken
import org.nyxlang.lexer.token.keyword.FunKeywordToken
import org.nyxlang.lexer.token.keyword.IfKeywordToken
import org.nyxlang.lexer.token.keyword.LoopKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.advanceBeforeAfter
import org.nyxlang.parser.ast.*
import org.nyxlang.parser.eval.expression.ArrayExprEval
import org.nyxlang.parser.eval.expression.ExprEval

/**
 * Evaluates atoms with the following semantic:
 *
 * <atom> ::= <bool> | <number> | <string> | <name>
 *          | '(' <expr> ')'
 *          | <arr-expr>
 *          | <if-expr>
 *          | <fun-declaration>
 *          | <loop-stmt>
 *          | <coroutine>
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

            IfKeywordToken::class -> IfEval(parser).eval()
            LoopKeywordToken::class -> LoopEval(parser).eval()
            LeftBracketToken::class -> ArrayExprEval(parser).eval()
            FunKeywordToken::class -> FunDeclarationEval(parser).eval()
            AsyncKeywordToken::class -> parser.advance { return AsyncNode(StatementEval(parser).eval()) }

            LeftParenthesisToken::class -> parser.advanceBeforeAfter { ExprEval(parser).eval() }

            else -> EmptyNode()
        }
    }
}
