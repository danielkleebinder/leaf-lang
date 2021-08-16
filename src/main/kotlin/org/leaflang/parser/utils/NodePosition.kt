package org.leaflang.parser.utils

import org.leaflang.error.ErrorPosition
import org.leaflang.lexer.token.Token

/**
 * Node position inside the program text.
 */
data class NodePosition(val row: Int,
                        val column: Int,
                        val position: Int)

/**
 * Creates an node position from the given [token].
 */
fun fromToken(token: Token) = ErrorPosition(token.position.row, token.position.column, token.position.position)