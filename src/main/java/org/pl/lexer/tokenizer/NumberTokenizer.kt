package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.exception.TokenizerException
import org.pl.lexer.token.IToken
import org.pl.lexer.token.NumberToken
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