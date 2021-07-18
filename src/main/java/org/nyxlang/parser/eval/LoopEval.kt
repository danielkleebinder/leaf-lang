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
 * <loop-stmt>  ::= 'loop' (<statement>)? (':' <expr>)? (':' <statement>)? <block-stmt>
 *
 */
class LoopEval(private val parser: IParser) : IEval {

    override fun eval(): LoopNode {
        var init: INode? = null
        var condition: INode? = null
        var step: INode? = null
        var body: INode

        loopHeadInit { init = StatementEval(parser).eval() }
        loopHeadCondition { condition = ExprEval(parser).eval() }
        loopHeadStep { step = StatementEval(parser).eval() }
        body = BlockEval(parser).eval()

        if (init != null && condition == null && step == null) {
            condition = init
            init = null
        }

        return LoopNode(init, condition, step, body)
    }

    /**
     * Evaluates the loop init expression or throws an exception if semantically incorrect.
     */
    private inline fun loopHeadInit(head: () -> Unit) {
        if (LoopKeywordToken::class != parser.token::class) {
            throw EvalException("Loop keyword 'loop' expected")
        }
        parser.advanceCursor()

        if (LeftCurlyBraceToken::class != parser.token::class) {
            head()
        }
    }

    /**
     * Evaluates the loop condition expression or throws an exception if semantically incorrect.
     */
    private inline fun loopHeadCondition(head: () -> Unit) {
        if (ColonToken::class == parser.token::class) parser.advanceCursor()
        if (LeftCurlyBraceToken::class == parser.token::class) return
        head()
    }

    /**
     * Evaluates the loop step expression or throws an exception if semantically incorrect.
     */
    private inline fun loopHeadStep(head: () -> Unit) {
        if (ColonToken::class == parser.token::class) parser.advanceCursor()
        if (LeftCurlyBraceToken::class == parser.token::class) return
        head()
    }
}