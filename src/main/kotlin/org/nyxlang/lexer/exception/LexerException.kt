package org.nyxlang.lexer.exception

import org.nyxlang.lexer.LexerError

/**
 * A lexer throws this exception if any errors occurred during lexical analysis.
 */
class LexerException(message: String, private val errors: Array<LexerError>) : Exception(message) {
    override fun toString() = "The lexical analysis detected some syntax errors and therefore failed (LexerException): ${
        errors
                .map { "\n$it" }
                .reduce { acc, s -> acc + s }
    }"
}