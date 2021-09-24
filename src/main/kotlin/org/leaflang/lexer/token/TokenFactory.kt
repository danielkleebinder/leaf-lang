package org.leaflang.lexer.token

import org.leaflang.lexer.source.ISource

/**
 * Concrete token factory implementation for a given source.
 */
class TokenFactory(private val source: ISource) : ITokenFactory {
    override fun newToken(kind: TokenType, value: Any?): Token {
        val position = TokenPosition(
                row = source.rowPosition,
                column = source.columnPosition,
                position = source.cursorPosition,
                source = source)
        return Token(kind, position, value)
    }
}