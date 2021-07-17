package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.CommaToken

/**
 * Tokenizes the symbol ','.
 */
class CommaTokenizer : ITokenizer {
    override fun matches(c: Char) = c == ','
    override fun tokenize(lexer: ILexer) = CommaToken()
}