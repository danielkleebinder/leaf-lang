package org.nyxlang.lexer.exception

/**
 * May occur during the lexical analysis.
 */
class TokenizerException(message: String, val location: Int) : Exception(message)