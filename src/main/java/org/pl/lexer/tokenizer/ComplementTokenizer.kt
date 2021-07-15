package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.ComplementToken

/**
 * Tokenizes the complement symbol '~'.
 */
class ComplementTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '~'
    override fun tokenize(lexer: ILexer) = ComplementToken()
}