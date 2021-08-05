package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.source.ISource
import org.nyxlang.lexer.source.advance
import org.nyxlang.lexer.source.advanceIf
import org.nyxlang.lexer.token.ITokenFactory
import org.nyxlang.lexer.token.TokenType

/**
 * Tokenizes the minus symbol '-'.
 */
class MinusTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '-'
    override fun tokenize(source: ISource, tokenFactory: ITokenFactory) = source.advance {
        source.advanceIf(source.symbol == '-') { return tokenFactory.newToken(TokenType.DECREMENT) }
        source.advanceIf(source.symbol == '>') { return tokenFactory.newToken(TokenType.RIGHT_ARROW) }
        tokenFactory.newToken(TokenType.MINUS)
    }
}