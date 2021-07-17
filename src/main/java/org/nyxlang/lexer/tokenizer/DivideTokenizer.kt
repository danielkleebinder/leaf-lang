package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.CommentToken
import org.nyxlang.lexer.token.IToken
import org.nyxlang.lexer.token.arithmetic.DivideToken

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