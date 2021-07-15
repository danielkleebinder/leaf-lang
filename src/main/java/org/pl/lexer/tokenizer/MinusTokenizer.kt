package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.arithmetic.MinusToken

/**
 * Tokenizes the minus symbol '-'.
 */
class MinusTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '-'
    override fun tokenize(lexer: ILexer) = MinusToken()
}