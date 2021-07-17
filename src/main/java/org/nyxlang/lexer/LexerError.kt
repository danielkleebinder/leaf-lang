package org.nyxlang.lexer


/**
 * A lexer error indicates at which [location] in the program code some error occurred. This
 * error is described by some given [message].
 */
class LexerError(private val message: String?, private val location: Int) {
    override fun toString() = "Lexer error at position $location: $message"
}