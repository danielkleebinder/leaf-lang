package org.nyxlang.lexer.token.keyword

import org.nyxlang.lexer.token.IToken

/**
 * Identifies the type instantiation keyword "new".
 */
class NewKeywordToken : IToken {
    override fun toString() = "NewKeywordToken(new)"
}