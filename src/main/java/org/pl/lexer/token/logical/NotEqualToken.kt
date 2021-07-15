package org.pl.lexer.token.logical

import org.pl.lexer.token.IToken

/**
 * Identifies the logical NOT EQUAL operator.
 */
class NotEqualToken : IToken {
    override fun toString() = "NotEqualToken{!=}"
}