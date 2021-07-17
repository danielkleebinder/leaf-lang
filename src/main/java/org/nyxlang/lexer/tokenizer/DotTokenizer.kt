package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.DotToken

/**
 * Tokenizes the symbol '.'.
 */
class DotTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '.'
    override fun tokenize(lexer: ILexer) = DotToken()
}