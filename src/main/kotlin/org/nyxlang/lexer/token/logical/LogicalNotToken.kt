package org.nyxlang.lexer.token.logical

import org.nyxlang.lexer.token.IToken

/**
 * Identifies the logical NOT operator.
 */
class LogicalNotToken : IToken {
    override fun toString() = "NotToken{!}"
}