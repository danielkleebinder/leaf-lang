package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.ColonToken
import org.nyxlang.lexer.token.StatementSeparatorToken
import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.bracket.RightCurlyBraceToken
import org.nyxlang.lexer.token.keyword.ElseKeywordToken
import org.nyxlang.lexer.token.keyword.WhenKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.WhenCase
import org.nyxlang.parser.ast.WhenNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the when semantics:
 *
 * <when-stmt>  ::= 'when' (<expr>)? '{' ((<expr> | 'else') ':' (<statement> | <block-stmt>) ))* '}'
 *
 */
class WhenEval(private val parser: IParser) : IEval {

    override fun eval(): WhenNode {
        val expr = ExprEval(parser)
        val statement = StatementEval(parser)
        val block = BlockEval(parser)

        val cases = arrayListOf<WhenCase>()
        var argument: INode? = null
        var match: INode
        var body: INode
        var elseCase: INode? = null

        whenHead { argument = expr.eval() }
        whenBody {
            while (StatementSeparatorToken::class == parser.token::class) {
                parser.advanceCursor()

                when (parser.token::class) {
                    RightCurlyBraceToken::class -> break
                    ElseKeywordToken::class -> parser.advance {
                        if (ColonToken::class != parser.token::class) throw EvalException("Colon expected after 'else' case")
                        parser.advanceCursor()
                        elseCase = statement.eval()
                    }
                    else -> {
                        match = expr.eval()
                        if (ColonToken::class != parser.token::class) throw EvalException("Colon expected after when case")
                        parser.advanceCursor()
                        body = statement.eval()
                        cases.add(WhenCase(match, body))
                    }
                }
            }
        }

        return WhenNode(argument, cases, elseCase)
    }

    /**
     * Evaluates the when argument expression or throws an exception if semantically incorrect.
     */
    private inline fun whenHead(head: () -> Unit) {
        if (WhenKeywordToken::class != parser.token::class) {
            throw EvalException("When keyword 'when' expected")
        }
        parser.advanceCursor()

        if (LeftCurlyBraceToken::class != parser.token::class) {
            head()
        }
    }

    private inline fun whenBody(body: () -> Unit) {
        if (LeftCurlyBraceToken::class != parser.token::class) {
            throw EvalException("Opening curly braces are required for 'when' body")
        }
        parser.advanceCursor()

        // Do we even have a non-empty body?
        if (RightCurlyBraceToken::class != parser.token::class) {
            body()
        }

        if (RightCurlyBraceToken::class != parser.token::class) {
            throw EvalException("Closing curly braces are required for 'when' body")
        }
        parser.advanceCursor()
    }
}