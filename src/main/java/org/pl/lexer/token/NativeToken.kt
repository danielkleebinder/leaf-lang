package org.pl.lexer.token

/**
 * Identifies the "native" semantic.
 */
class NativeToken(val programCode: String) : IToken {
    override fun toString() = "NativeToken{code=$programCode}"
}