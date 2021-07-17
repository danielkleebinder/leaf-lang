package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.CommentToken
import org.pl.lexer.token.IToken
import org.pl.lexer.token.arithmetic.DivideToken

/**
 * Tokenizes the symbol '/'.
 */
class DivideTokenizer : ITokenizer {
    override fun matches(c: Char) = c == '/'
    override fun tokenize(lexer: ILexer): IToken {
        if (lexer.peekNextSymbol == '/') {
            val comment = StringBuilder()
            lexer.advanceCursor(2)
            while (!lexer.isEndOfProgram && lexer.symbol != '\n') {
                comment.append(lexer.symbol)
                lexer.advanceCursor()
            }
            return CommentToken(comment.toString().trim())
        }
        return DivideToken()
    }
}