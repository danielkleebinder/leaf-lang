package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.source.ISource
import org.nyxlang.lexer.source.advance
import org.nyxlang.lexer.token.ITokenFactory
import org.nyxlang.lexer.token.TokenType

/**
 * Tokenizes the remainder symbol '%'.
 */
class RemainderTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '%'
    override fun tokenize(source: ISource, tokenFactory: ITokenFactory) = source.advance {
        tokenFactory.newToken(TokenType.REM)
    }
}