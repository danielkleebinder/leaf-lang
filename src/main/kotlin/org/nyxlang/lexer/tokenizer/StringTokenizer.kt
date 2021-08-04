package org.nyxlang.lexer.tokenizer

import org.nyxlang.error.ErrorCode
import org.nyxlang.lexer.source.ISource
import org.nyxlang.lexer.token.ITokenFactory
import org.nyxlang.lexer.token.Token
import org.nyxlang.lexer.token.TokenType

/**
 * Tokenizes a (multi-line) string.
 */
class StringTokenizer : ITokenizer {

    companion object {
        private val escapeCharacters = hashMapOf(
                Pair('n', '\n'),
                Pair('t', '\t'))
    }

    override fun matches(c: Char) = c == '"'

    override fun tokenize(source: ISource, tokenFactory: ITokenFactory): Token {
        var escape = false
        val string = StringBuilder()

        source.advanceCursor()
        while (!source.isEndOfProgram && (!matches(source.symbol) || escape)) {
            if (escape) {
                string.append(escapeCharacters.getOrDefault(source.symbol, source.symbol))
                escape = false
            } else {
                if (source.symbol == '\\') {
                    escape = true
                } else {
                    string.append(source.symbol)
                }
            }
            source.advanceCursor()
        }

        if (source.symbol != '"') {
            return tokenFactory.newToken(TokenType.ERROR, ErrorCode.UNEXPECTED_STRING_TOKEN)
        }

        source.advanceCursor()
        return tokenFactory.newToken(TokenType.STRING, string.toString())
    }
}