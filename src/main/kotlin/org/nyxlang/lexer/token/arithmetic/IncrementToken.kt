package org.nyxlang.lexer.token.arithmetic

import org.nyxlang.lexer.token.IToken

/**
 * Identifies increment token.
 */
class IncrementToken : IToken {
    override fun toString() = "IncrementToken(++)"
}