package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.ComplementToken

/**
 * Tokenizes the complement symbol '~'.
 */
class ComplementTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '~'
    override fun tokenize(lexer: ILexer) = ComplementToken()
}