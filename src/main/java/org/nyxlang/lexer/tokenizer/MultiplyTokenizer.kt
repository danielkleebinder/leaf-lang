package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.arithmetic.MultiplyToken

/**
 * Tokenizes the multiply symbol '*'.
 */
class MultiplyTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '*'
    override fun tokenize(lexer: ILexer) = MultiplyToken()
}