package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.arithmetic.ModToken

/**
 * Tokenizes the minus symbol '%'.
 */
class ModTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '%'
    override fun tokenize(lexer: ILexer) = ModToken()
}