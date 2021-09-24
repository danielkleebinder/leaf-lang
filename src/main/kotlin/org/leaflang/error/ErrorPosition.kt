package org.leaflang.error

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.token.Token
import org.leaflang.parser.ast.INode

/**
 * The position in the program where the error occurred.
 */
data class ErrorPosition(val row: Int,
                         val column: Int,
                         val position: Int,
                         val source: ISource)

/**
 * Creates an error position from the given [token].
 */
fun fromToken(token: Token) = with(token.position) { ErrorPosition(row, column, position, source) }

/**
 * Creates an error position from the given [node].
 */
fun fromNode(node: INode) = with(node.position) { ErrorPosition(row, column, position, source) }
