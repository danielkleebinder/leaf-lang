package org.leaflang.lexer.tokenizer

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.source.advance
import org.leaflang.lexer.source.advanceIf
import org.leaflang.lexer.token.ITokenFactory
import org.leaflang.lexer.token.TokenType

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