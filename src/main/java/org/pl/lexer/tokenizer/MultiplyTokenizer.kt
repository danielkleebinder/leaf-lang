package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.arithmetic.MultiplyToken

/**
 * Tokenizes the multiply symbol '*'.
 */
class MultiplyTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '*'
    override fun tokenize(lexer: ILexer) = MultiplyToken()
}