package org.leaflang.lexer.token

/**
 * Used to create new tokens.
 */
interface ITokenFactory {

    /**
     * Creates a new token of the given type.
     */
    fun newToken(kind: TokenType, value: Any? = null): Token
}