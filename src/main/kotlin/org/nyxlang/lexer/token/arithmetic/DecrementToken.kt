package org.nyxlang.lexer.token.arithmetic

import org.nyxlang.lexer.token.IToken

/**
 * Identifies decrement token.
 */
class DecrementToken : IToken {
    override fun toString() = "DecrementToken(--)"
}