package org.nyxlang.error

import org.nyxlang.lexer.token.Token

/**
 * The position in the program where the error occurred.
 */
class ErrorPosition(val row: Int,
                    val column: Int) {
    val position = row * column
}

/**
 * Creates an error position from the given [token].
 */
fun fromToken(token: Token) = ErrorPosition(token.position.row, token.position.column)