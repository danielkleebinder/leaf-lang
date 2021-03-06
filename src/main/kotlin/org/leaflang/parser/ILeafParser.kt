package org.leaflang.parser

import org.leaflang.error.ErrorCode
import org.leaflang.error.IErrorHandler
import org.leaflang.lexer.token.Token
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.fromToken

/**
 * The parser uses the abstract tokens from the lexical analysis
 * to build an abstract syntax tree which can be used for further
 * analysis.
 */
interface ILeafParser {

    /**
     * Parses the given array of [tokens].
     */
    fun parse(tokens: Array<Token>): INode?

    /**
     * Advances the cursor position [by] the given amount.
     */
    fun advanceCursor(by: Int = 1): Int

    /**
     * Skips the next sequence of new lines.
     */
    fun skipNewLines()

    /**
     * Flags the current token with an error.
     */
    fun flagError(errorCode: ErrorCode)

    /**
     * The parsers local error handler.
     */
    var errorHandler: IErrorHandler?

    /**
     * The current token.
     */
    val token: Token

    /**
     * Peeks the next token without advancing the cursor.
     */
    val peekNextToken: Token

    /**
     * Returns the current token and then advances one step.
     */
    val tokenAndAdvance: Token
        get() {
            val result = token
            advanceCursor()
            return result
        }

    /**
     * Returns a new node position for the current token.
     */
    fun nodePosition() = fromToken(token)
}

/**
 * Advances the cursor by one and runs the given [fn] function. The parameter
 * of the inline function is the new current token.
 */
inline fun <T> ILeafParser.advance(fn: (token: Token) -> T): T = advanceBy(1, fn)

/**
 * Advances the cursor by one token and skips all new lines afterwards before executing
 * the given [fn] with the newest token.
 */
inline fun <T> ILeafParser.advanceAndSkipNewLines(fn: (token: Token) -> T): T {
    advanceCursor()
    skipNewLines()
    return fn(token)
}

/**
 * Advances the cursor [by] the given amount and runs the given [fn] function.
 * The parameter of the inline function is the new current token.
 */
inline fun <T> ILeafParser.advanceBy(by: Int, fn: (token: Token) -> T): T {
    advanceCursor(by)
    return fn(token)
}

/**
 * Advances the cursor by one and runs the given [fn] function if the given
 * condition is met. The parameter of the inline function is the new current token.
 */
inline fun <T> ILeafParser.advanceIf(cond: Boolean, fn: (token: Token) -> T): T? {
    if (cond) {
        advanceCursor()
        return fn(token)
    }
    return null
}