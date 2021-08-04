package org.nyxlang.lexer.token

import org.nyxlang.lexer.source.ISource

/**
 * Concrete token factory implementation for a given source.
 */
class TokenFactory(private val source: ISource) : ITokenFactory {
    override fun newToken(kind: TokenType, value: Any?): Token {
        val position = TokenPosition(source.rowPosition, source.columnPosition)
        return Token(kind, position, value)
    }
}