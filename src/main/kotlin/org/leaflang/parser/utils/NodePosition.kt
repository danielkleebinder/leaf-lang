package org.leaflang.parser.utils

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.token.Token
import org.leaflang.lexer.token.TokenPosition

/**
 * Node position inside the program text. The [source] is also stored here to provide
 * better error messages and more detailed information about the node in general. Technically,
 * it would be sufficient to store the source at the root node of a sub tree of a new source, but
 * because this is only a reference to a source object and not a new source object, memory
 * consumption should not be a problem at all.
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