package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.exception.TokenizerException
import org.nyxlang.lexer.token.DotToken
import org.nyxlang.lexer.token.IToken
import org.nyxlang.lexer.token.NumberToken
import java.math.BigDecimal

/**
 * Tokenizes a number (either decimal or int).
 */
class NumberTokenizer : ITokenizer {

    private val numberStart = Regex("[0-9.]")
    private val numberPart = Regex("[0-9._]")

    override fun matches(c: Char) = numberStart.matches(c.toString())

    override fun tokenize(lexer: ILexer): IToken {
        var decimalPointCount = 0
        val numberBuilder = StringBuilder()
        while (!lexer.isEndOfProgram && numberPart.matches(lexer.symbol.toString())) {
            if (lexer.symbol == '.') {
                decimalPointCount++
            }
            if (lexer.symbol != '_') {
                numberBuilder.append(lexer.symbol)
            }
            lexer.advanceCursor()
        }
        lexer.advanceCursor(-1)
        val numberStr = numberBuilder.toString()

        if (decimalPointCount == 1 && numberStr.length == 1) return DotToken()
        if (decimalPointCount <= 1) return NumberToken(BigDecimal(numberStr))
        throw TokenizerException("More than one decimal point not allowed at \"$numberStr\", did you mean to separate it?", lexer.cursorPosition)
    }
}