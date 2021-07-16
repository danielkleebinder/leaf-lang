package org.pl.parser.eval

import org.pl.lexer.token.bracket.LeftCurlyBraceToken
import org.pl.lexer.token.bracket.RightCurlyBraceToken
import org.pl.lexer.token.keyword.ConditionalKeywordToken
import org.pl.lexer.token.keyword.ElseKeywordToken
import org.pl.parser.IParser
import org.pl.parser.ast.INode
import org.pl.parser.ast.IfCase
import org.pl.parser.ast.IfNode
import org.pl.parser.exception.EvalException

/**
 * Evaluates the conditional semantics:
 *
 * <conditional-expr> ::= 'if' <expr>         '{' <statement-list> '}'
 *                        ('else' 'if' <expr> '{' <statement-list> '}')*
 *                        ('else'             '{' <statement-list> '}')?
 *
 */
class ConditionalEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val expr = ExprEval(parser)
        val statementList = StatementListEval(parser)

        val cases = arrayListOf<IfCase>()
        var condition: INode? = null
        var body: INode? = null
        var elseCase: INode? = null

        // Evaluate first if <expr> { <statement-list> } block
        conditionalHead { condition = expr.eval() }
        conditionalBody {
            body = statementList.eval()
            cases.add(IfCase(condition, body))
        }

        // Evaluate further case blocks
        while (ElseKeywordToken::class == parser.token::class) {
            parser.advanceCursor()

            if (ConditionalKeywordToken::class == parser.token::class) {
                parser.advanceCursor()

                condition = expr.eval()
                conditionalBody {
                    body = statementList.eval()
                    cases.add(IfCase(condition, body))
                }
            } else {
                conditionalBody { elseCase = statementList.eval() }
            }
        }

        return IfNode(cases, elseCase)
    }

    /**
     * Evaluates the conditional head semantics and throws exceptions if semantics are incorrect or
     * executes the body if everything is fine.
     */
    private inline fun conditionalHead(head: () -> Unit) {
        if (ConditionalKeywordToken::class != parser.token::class) {
            throw EvalException("Conditional keyword 'if' expected")
        }
        parser.advanceCursor()

        if (LeftCurlyBraceToken::class != parser.token::class) {
            head()
        }
    }

    /**
     * Evaluates the conditional body semantics and throws exceptions if semantics are incorrect or
     * executes the body if everything is fine.
     */
    private inline fun conditionalBody(body: () -> Unit) {
        if (LeftCurlyBraceToken::class != parser.token::class) {
            throw EvalException("Opening curly braces are required for 'if' body")
        }
        parser.advanceCursor()

        // Do we even have a non-empty body?
        if (RightCurlyBraceToken::class != parser.token::class) {
            body()
        }

        if (RightCurlyBraceToken::class != parser.token::class) {
            throw EvalException("Closing curly braces are required for 'if' body")
        }
        parser.advanceCursor()
    }
}