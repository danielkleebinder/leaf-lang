package org.leaflang.lexer.token

/**
 * Tokens are a programming language specific alphabet that are used to
 * add meaning to program text.
 */
data class Token(val kind: TokenType,
                 val position: TokenPosition,
                 val value: Any? = null) {
    override fun toString() = "$kind${if (value != null) "(val=$value)" else ""}"
}