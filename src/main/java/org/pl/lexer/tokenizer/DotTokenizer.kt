package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.DotToken

/**
 * Tokenizes the symbol '.'.
 */
class DotTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '.'
    override fun tokenize(lexer: ILexer) = DotToken()
}