package org.leaflang.lexer.tokenizer

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.source.advance
import org.leaflang.lexer.token.ITokenFactory
import org.leaflang.lexer.token.TokenType

/**
 * Tokenizes the times symbol '*'.
 */
class TimesTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '*'
    override fun tokenize(source: ISource, tokenFactory: ITokenFactory) = source.advance {
        tokenFactory.newToken(TokenType.TIMES)
    }
}