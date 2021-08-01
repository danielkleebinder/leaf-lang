package org.nyxlang.lexer.token.arithmetic

import org.nyxlang.lexer.token.IToken

/**
 * Identifies number addition.
 */
class PlusToken : IToken {
    override fun toString() = "PlusToken(+)"
}