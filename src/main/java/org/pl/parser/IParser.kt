package org.pl.parser

import org.pl.lexer.token.IToken
import org.pl.parser.ast.INode

/**
 * The parser uses the abstract tokens from the lexical analysis
 * to build an abstract syntax tree which can be used for further
 * analysis.
 */
interface IParser {

    /**
     * Parses the given array of [tokens].
     */
    fun parse(tokens: Array<IToken>): INode?

    /**
     * Advances the cursor by one position.
     */
    fun advanceCursor() = advanceCursor(1)

    /**
     * Advances the cursor position [by] the given amount.
     */
    fun advanceCursor(by: Int): Int

    val cursorPosition: Int
    fun hasNextToken(): Boolean
    val token: IToken
    val peekNextToken: IToken

    /**
     * Returns the current token and then advances one step.
     */
    val tokenAndAdvance: IToken
        get() {
            val result = token
            advanceCursor()
            return result
        }
}

/**
 * Advances the cursor by one and runs the given [body] function. The parameter
 * of the inline function is the new current token.
 */
inline fun <T> IParser.advance(body: (token: IToken) -> T): T {
    advanceCursor()
    return body(token)
}

/**
 * Advances the cursor by one and runs the given [body] function if the given
 * condition is met. The parameter of the inline function is the new current token.
 */
inline fun <T> IParser.advanceIf(cond: Boolean, body: (token: IToken) -> T): T? {
    if (cond) {
        advanceCursor()
        return body(token)
    }
    return null
}