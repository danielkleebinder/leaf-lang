package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.arithmetic.DivideToken

/**
 * Tokenizes the symbol '/'.
 */
class DivideTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '/'
    override fun tokenize(lexer: ILexer) = DivideToken()
}