package org.leaflang.lexer.token

import org.leaflang.lexer.source.ISource

/**
 * Token position inside the program text.
 */
data class TokenPosition(val row: Int,
                         val column: Int,
                         val position: Int,
                         val source: ISource)