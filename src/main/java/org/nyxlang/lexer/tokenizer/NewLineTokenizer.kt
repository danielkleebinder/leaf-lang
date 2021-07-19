package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.NewLineToken

/**
 * Tokenizes the new line symbol '\n'.
 */
class NewLineTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '\n'
    override fun tokenize(lexer: ILexer) = NewLineToken()
}