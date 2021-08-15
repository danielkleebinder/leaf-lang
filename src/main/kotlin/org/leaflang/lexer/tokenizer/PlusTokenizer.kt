package org.leaflang.lexer.tokenizer

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.source.advance
import org.leaflang.lexer.source.advanceIf
import org.leaflang.lexer.token.ITokenFactory
import org.leaflang.lexer.token.TokenType

/**
 * Tokenizes the plus and increment symbols ('+' and '++').
 */
class PlusTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '+'
    override fun tokenize(source: ISource, tokenFactory: ITokenFactory) = source.advance {
        source.advanceIf(source.symbol == '+') { return tokenFactory.newToken(TokenType.INCREMENT) }
        tokenFactory.newToken(TokenType.PLUS)
    }
}