package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.source.ISource
import org.nyxlang.lexer.token.ITokenFactory
import org.nyxlang.lexer.token.Token
import org.nyxlang.lexer.token.TokenType

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
        return tokenFactory.newToken(TokenType.DIV)
    }
}