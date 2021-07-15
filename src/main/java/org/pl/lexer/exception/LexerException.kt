package org.pl.lexer.exception

import org.pl.lexer.LexerError

/**
 * A lexer throws this exception if any errors occurred during lexical analysis.
 */
class LexerException(message: String, private val errors: Array<LexerError>) : Exception(message) {
    override fun toString() = "LexerException(errors=${errors.contentToString()})"
}