package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.arithmetic.ModToken

/**
 * Tokenizes the minus symbol '%'.
 */
class ModTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '%'
    override fun tokenize(lexer: ILexer) = ModToken()
}