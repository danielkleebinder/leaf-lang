package org.nyxlang.lexer.token.keyword

import org.nyxlang.lexer.token.IToken

/**
 * Identifies the "string" keyword.
 */
class StringKeywordToken : IToken {
    override fun toString() = "StringKeywordToken(string)"
}