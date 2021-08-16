package org.leaflang.lexer.tokenizer

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.token.ITokenFactory
import org.leaflang.lexer.token.Token
import org.leaflang.lexer.token.TokenType

/**
 * Tokenizes the symbol '/'.
 */
class SlashTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '/'
    override fun tokenize(source: ISource, tokenFactory: ITokenFactory): Token {
        source.advanceCursor()

        if (source.symbol == '/') {
            source.advanceCursor()
            val comment = StringBuilder()
            while (!source.isEndOfProgram && source.symbol != '\n') {
                comment.append(source.symbol)
                source.advanceCursor()
            }
            return tokenFactory.newToken(TokenType.COMMENT, comment.toString().trim())
        }

        if (source.symbol == '*') {
            source.advanceCursor()
            val comment = StringBuilder()
            while (!source.isEndOfProgram) {
                if (source.symbol == '*' && source.peekNextSymbol == '/') {
                    source.advanceCursor()
                    source.advanceCursor()
                    break
                }
                comment.append(source.symbol)
                source.advanceCursor()
            }
            return tokenFactory.newToken(TokenType.COMMENT, comment.toString().trim())
        }

        return tokenFactory.newToken(TokenType.DIV)
    }
}