package org.nyxlang.lexer.token

/**
 * Token position inside the program text.
 */
data class TokenPosition(val row: Int,
                         val column: Int) {

    /**
     * The total position computed from the row and column.
     */
    val position = row * column
}