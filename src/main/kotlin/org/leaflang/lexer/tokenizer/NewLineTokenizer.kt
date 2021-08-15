package org.leaflang.lexer.tokenizer

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.source.advance
import org.leaflang.lexer.token.ITokenFactory
import org.leaflang.lexer.token.TokenType

/**
 * Tokenizes the new line symbol '\n'.
 */
class NewLineTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '\n'
    override fun tokenize(source: ISource, tokenFactory: ITokenFactory) = source.advance {
        tokenFactory.newToken(TokenType.NEW_LINE)
    }
}