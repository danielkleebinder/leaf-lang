package org.leaflang.parser.utils

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.token.Token
import org.leaflang.lexer.token.TokenPosition

/**
 * Node position inside the program text.
 */
data class NodePosition(val row: Int,
                        val column: Int,
                        val position: Int,
                        val source: ISource)

/**
 * Creates a new node position object from the given [token].
 */
fun fromToken(token: Token) = fromTokenPosition(token.position)

/**
 * Creates a new node position object from the given [pos].
 */
fun fromTokenPosition(pos: TokenPosition) = with(pos) { NodePosition(row, column, position, source) }