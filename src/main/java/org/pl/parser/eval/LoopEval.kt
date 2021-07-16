package org.pl.parser.eval

import org.pl.lexer.token.bracket.LeftCurlyBraceToken
import org.pl.lexer.token.bracket.RightCurlyBraceToken
import org.pl.lexer.token.keyword.LoopKeywordToken
import org.pl.parser.IParser
import org.pl.parser.ast.INode
import org.pl.parser.ast.LoopNode
import org.pl.parser.exception.EvalException

/**
 * Evaluates the loop semantics:
 *
 * <loop-expr> ::= 'loop' (<expr>)? '{' <statement-list> '}'
 *
 */
class LoopEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        var condition: INode? = null
        var body: INode? = null

        loopHead { condition = ExprEval(parser).eval() }
        loopBody { body = StatementListEval(parser).eval() }

        return LoopNode(condition, body)
    }

    /**
     * Evaluates the loop head semantics and throws exceptions if semantics are incorrect or
     * executes the head if everything is fine.
     */
    private inline fun loopHead(head: () -> Unit) {
        if (LoopKeywordToken::class != parser.token::class) {
            throw EvalException("Loop keyword 'loop' expected")
        }
        parser.advanceCursor()

        if (LeftCurlyBraceToken::class != parser.token::class) {
            head()
        }
    }

    /**
     * Evaluates the loop body semantics and throws exceptions if semantics are incorrect or
     * executes the body if everything is fine.
     */
    private inline fun loopBody(body: () -> Unit) {
        if (LeftCurlyBraceToken::class != parser.token::class) {
            throw EvalException("Opening curly braces are required for 'loop' body")
        }
        parser.advanceCursor()

        // Do we even have a non-empty body?
        if (RightCurlyBraceToken::class != parser.token::class) {
            body()
        }

        if (RightCurlyBraceToken::class != parser.token::class) {
            throw EvalException("Closing curly braces are required for 'loop' body")
        }
        parser.advanceCursor()
    }
}