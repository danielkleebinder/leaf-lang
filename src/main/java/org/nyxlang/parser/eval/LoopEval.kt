package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.ColonToken
import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.bracket.RightCurlyBraceToken
import org.nyxlang.lexer.token.keyword.LoopKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.LoopNode
import org.nyxlang.parser.ast.VarDeclareNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the loop semantics:
 *
 * <loop-expr>   ::= 'loop' (<expr>)? (':' <expr>)? (':' <expr>)? '{' <statement-list> '}'
 *
 */
class LoopEval(private val parser: IParser) : IEval {

    override fun eval(): LoopNode {
        var init: INode? = null
        var condition: INode? = null
        var step: INode? = null
        var body: INode? = null

        loopHeadInit { init = StatementEval(parser).eval() }
        loopHeadCondition { condition = ExprEval(parser).eval() }
        loopHeadStep { step = StatementEval(parser).eval() }
        println(init)
        println(condition)
        println(step)
        loopBody { body = StatementListEval(parser).eval() }

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