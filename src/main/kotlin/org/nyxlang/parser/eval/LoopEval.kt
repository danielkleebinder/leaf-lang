package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.ColonToken
import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.keyword.LoopKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.LoopNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the loop semantics:
 *
 * <loop-stmt> ::= 'loop' ((NL)* <loop-init>)?
 *                        ((NL)* <loop-cond>)?
 *                        ((NL)* <loop-step>)?
 *                        ((NL)* <loop-body>)
 *
 * <loop-init> ::= <statement>
 * <loop-cond> ::= ':' (NL)* <expr>
 * <loop-step> ::= ':' (NL)* <statement>
 * <loop-body> ::= <block>
 *
 */
class LoopEval(private val parser: IParser) : IEval {

    override fun eval(): LoopNode {
        var init: INode? = null
        var cond: INode? = null
        var step: INode? = null
        var body: INode? = null

        loopInit { init = StatementEval(parser).eval() }
        loopCond { cond = ExprEval(parser).eval() }
        loopStep { step = StatementEval(parser).eval() }
        loopBody { body = BlockEval(parser).eval() }

        if (init != null && cond == null && step == null) {
            cond = init
            init = null
        }

        return LoopNode(init, cond, step, body!!)
    }

    /**
     * Evaluates the loop init expression or throws an exception if semantically incorrect.
     */
    private inline fun loopInit(head: () -> Unit) {
        if (LoopKeywordToken::class != parser.token::class) {
            throw EvalException("Loop keyword 'loop' expected")
        }
        parser.advanceCursor()
        parser.skipNewLines()

        if (LeftCurlyBraceToken::class != parser.token::class) {
            head()
        }
    }

    /**
     * Evaluates the loop condition expression or throws an exception if semantically incorrect.
     */
    private inline fun loopCond(head: () -> Unit) {
        if (ColonToken::class == parser.token::class) parser.advanceCursor()
        parser.skipNewLines()
        if (LeftCurlyBraceToken::class == parser.token::class) return
        parser.skipNewLines()
        head()
    }

    /**
     * Evaluates the loop step expression or throws an exception if semantically incorrect.
     */
    private inline fun loopStep(head: () -> Unit) {
        if (ColonToken::class == parser.token::class) parser.advanceCursor()
        parser.skipNewLines()
        if (LeftCurlyBraceToken::class == parser.token::class) return
        parser.skipNewLines()
        head()
    }

    /**
     * Evaluates the loop body.
     */
    private inline fun loopBody(body: () -> Unit) {
        parser.skipNewLines()
        body()
    }
}