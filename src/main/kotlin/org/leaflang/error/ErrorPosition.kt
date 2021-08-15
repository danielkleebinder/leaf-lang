package org.leaflang.error

import org.leaflang.lexer.token.Token

/**
 * The position in the program where the error occurred.
 */
data class ErrorPosition(val row: Int,
                         val column: Int,
                         val position: Int)

/**
 * Creates an error position from the given [token].
 */
fun fromToken(token: Token) = ErrorPosition(token.position.row, token.position.column, token.position.position)