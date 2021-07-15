package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.ColonToken

/**
 * Tokenizes the symbol ':'.
 */
class ColonTokenizer : ITokenizer {
    override fun matches(c: Char) = c == ':'
    override fun tokenize(lexer: ILexer) = ColonToken()
}