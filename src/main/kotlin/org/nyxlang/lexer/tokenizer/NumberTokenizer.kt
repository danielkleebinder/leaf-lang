package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.exception.TokenizerException
import org.nyxlang.lexer.token.IToken
import org.nyxlang.lexer.token.NumberToken
import java.math.BigDecimal

/**
 * Tokenizes a number (either decimal or int).
 */
class NumberTokenizer : ITokenizer {

    override fun matches(c: Char) = Character.isDigit(c) || c == '.'

    override fun tokenize(lexer: ILexer): IToken {
        var decimalPointCount = 0
        val numberBuilder = StringBuilder()
        while (!lexer.isEndOfProgram && matches(lexer.symbol)) {
            if (lexer.symbol == '.') {
                decimalPointCount++
            }
            numberBuilder.append(lexer.symbol)
            lexer.advanceCursor()
        }
        lexer.advanceCursor(-1)
        val numberStr = numberBuilder.toString()
        if (decimalPointCount <= 1) {
            return NumberToken(BigDecimal(numberStr))
        }
        throw TokenizerException("More than one decimal point not allowed at \"$numberStr\", did you mean to separate it?", lexer.cursorPosition)
    }
}