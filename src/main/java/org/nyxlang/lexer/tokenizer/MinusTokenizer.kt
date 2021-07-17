package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.ArrowToken
import org.nyxlang.lexer.token.IToken
import org.nyxlang.lexer.token.arithmetic.DecrementToken
import org.nyxlang.lexer.token.arithmetic.MinusToken

/**
 * Tokenizes the minus symbol '-'.
 */
class MinusTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '-'
    override fun tokenize(lexer: ILexer): IToken {
        if (lexer.peekNextSymbol == '-') {
            lexer.advanceCursor()
            return DecrementToken()
        }
        if (lexer.peekNextSymbol == '>') {
            lexer.advanceCursor()
            return ArrowToken()
        }
        return MinusToken()
    }
}