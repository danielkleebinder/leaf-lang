package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.IToken
import org.nyxlang.lexer.token.arithmetic.IncrementToken
import org.nyxlang.lexer.token.arithmetic.PlusToken

/**
 * Tokenizes the plus and increment symbols ('+' and '++').
 */
class PlusTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '+'
    override fun tokenize(lexer: ILexer): IToken {
        if (lexer.peekNextSymbol == '+') {
            lexer.advanceCursor()
            return IncrementToken()
        }
        return PlusToken()
    }
}