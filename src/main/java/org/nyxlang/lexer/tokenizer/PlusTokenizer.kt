package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.arithmetic.PlusToken

/**
 * Tokenizes the plus symbol '+'.
 */
class PlusTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '+'
    override fun tokenize(lexer: ILexer) = PlusToken()
}