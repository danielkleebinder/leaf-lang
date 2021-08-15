package org.leaflang.lexer.tokenizer

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.token.ITokenFactory
import org.leaflang.lexer.token.Token
import org.leaflang.lexer.token.TokenType
import org.leaflang.error.ErrorCode

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