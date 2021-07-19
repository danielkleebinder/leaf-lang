package org.nyxlang.parser

import org.nyxlang.lexer.token.IToken
import org.nyxlang.parser.ast.INode

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
     * Advances the cursor position [by] the given amount.
     */
    fun advanceCursor(by: Int = 1): Int

    /**
     * The current token.
     */
    val token: IToken

    /**
     * Peeks the next token without advancing the cursor.
     */
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
inline fun <T> IParser.advance(body: (token: IToken) -> T): T = advanceBy(1, body)

/**
 * Advances the cursor [by] the given amount and runs the given [body] function.
 * The parameter of the inline function is the new current token.
 */
inline fun <T> IParser.advanceBy(by: Int, body: (token: IToken) -> T): T {
    advanceCursor(by)
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