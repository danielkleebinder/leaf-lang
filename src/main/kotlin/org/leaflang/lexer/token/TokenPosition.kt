package org.leaflang.lexer.token

/**
 * Token position inside the program text.
 */
data class TokenPosition(val row: Int,
                         val column: Int,
                         val position: Int)