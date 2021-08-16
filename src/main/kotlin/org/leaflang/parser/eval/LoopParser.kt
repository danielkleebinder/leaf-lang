package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.LoopNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the 'loop' syntax:
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
class LoopParser(private val parser: ILeafParser,
                 private val parserFactory: IParserFactory) : IParser {

    override fun parse(): LoopNode {
        val statementParser = parserFactory.statementParser
        val expressionParser = parserFactory.expressionParser
        val blockParser = parserFactory.blockParser

        var init: INode? = null
        var cond: INode? = null
        var step: INode? = null
        var body: INode? = null

        loopInit { init = statementParser.parse() }
        loopCond { cond = expressionParser.parse() }
        loopStep { step = statementParser.parse() }
        loopBody { body = blockParser.parse() }

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
        if (TokenType.KEYWORD_LOOP != parser.token.kind) {
            parser.flagError(ErrorCode.MISSING_KEYWORD_LOOP)
        }
        parser.advanceCursor()
        parser.skipNewLines()

        if (TokenType.LEFT_CURLY_BRACE != parser.token.kind) {
            head()
        }
    }

    /**
     * Evaluates the loop condition expression or throws an exception if semantically incorrect.
     */
    private inline fun loopCond(head: () -> Unit) {
        if (TokenType.COLON == parser.token.kind) parser.advanceCursor()
        parser.skipNewLines()
        if (TokenType.LEFT_CURLY_BRACE == parser.token.kind) return
        parser.skipNewLines()
        head()
    }

    /**
     * Evaluates the loop step expression or throws an exception if semantically incorrect.
     */
    private inline fun loopStep(head: () -> Unit) {
        if (TokenType.COLON == parser.token.kind) parser.advanceCursor()
        parser.skipNewLines()
        if (TokenType.LEFT_CURLY_BRACE == parser.token.kind) return
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