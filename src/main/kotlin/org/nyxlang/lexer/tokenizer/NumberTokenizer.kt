package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.source.ISource
import org.nyxlang.lexer.token.ITokenFactory
import org.nyxlang.lexer.token.Token
import org.nyxlang.lexer.token.TokenType
import org.nyxlang.error.ErrorCode

/**
 * Tokenizes a number (either decimal or int).
 */
class NumberTokenizer : ITokenizer {

    private val numberStart = Regex("[0-9.]")
    private val numberPart = Regex("[0-9._]")

    override fun matches(c: Char) = numberStart.matches(c.toString())

    override fun tokenize(source: ISource, tokenFactory: ITokenFactory): Token {
        var decimalPointCount = 0
        val numberBuilder = StringBuilder()
        while (!source.isEndOfProgram && numberPart.matches(source.symbol.toString())) {
            if (source.symbol == '.') decimalPointCount++
            if (source.symbol != '_') numberBuilder.append(source.symbol)
            source.advanceCursor()
        }
        val numberStr = numberBuilder.toString()
        if (decimalPointCount == 1 && numberStr.length == 1) return tokenFactory.newToken(TokenType.DOT)
        if (decimalPointCount <= 1) return tokenFactory.newToken(TokenType.NUMBER, numberStr.toDouble())
        return tokenFactory.newToken(TokenType.ERROR, ErrorCode.UNEXPECTED_NUMBER_TOKEN)
    }
}