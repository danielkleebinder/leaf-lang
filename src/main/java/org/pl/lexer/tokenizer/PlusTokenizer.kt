package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.arithmetic.PlusToken

/**
 * Tokenizes the plus symbol '+'.
 */
class PlusTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '+'
    override fun tokenize(lexer: ILexer) = PlusToken()
}