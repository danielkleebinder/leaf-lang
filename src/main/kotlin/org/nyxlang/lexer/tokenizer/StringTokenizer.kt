package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.IToken
import org.nyxlang.lexer.token.StringToken

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

    override fun tokenize(lexer: ILexer): IToken {
        var escape = false
        val string = StringBuilder()

        lexer.advanceCursor()
        while (!lexer.isEndOfProgram && (!matches(lexer.symbol) || escape)) {
            if (escape) {
                string.append(escapeCharacters.getOrDefault(lexer.symbol, lexer.symbol))
                escape = false
            } else {
                if (lexer.symbol == '\\') {
                    escape = true
                } else {
                    string.append(lexer.symbol)
                }
            }
            lexer.advanceCursor()
        }

        return StringToken(string.toString())
    }
}