package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.ColonToken

/**
 * Tokenizes the symbol ':'.
 */
class ColonTokenizer : ITokenizer {
    override fun matches(c: Char) = c == ':'
    override fun tokenize(lexer: ILexer) = ColonToken()
}