package org.pl.lexer.token.logical

import org.pl.lexer.token.IToken

/**
 * Identifies the logical NOT operator.
 */
class LogicalNotToken : IToken {
    override fun toString() = "NotToken{!}"
}