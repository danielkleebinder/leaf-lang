package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.source.ISource
import org.nyxlang.lexer.source.advance
import org.nyxlang.lexer.token.ITokenFactory
import org.nyxlang.lexer.token.TokenType

/**
 * Tokenizes the new line symbol '\n'.
 */
class NewLineTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '\n'
    override fun tokenize(source: ISource, tokenFactory: ITokenFactory) = source.advance {
        tokenFactory.newToken(TokenType.NEW_LINE)
    }
}