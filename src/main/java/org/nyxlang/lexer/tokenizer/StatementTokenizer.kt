package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.StatementSeparatorToken

/**
 * Tokenizes the statement separation symbol ';'.
 */
class StatementTokenizer : ITokenizer {
    override fun matches(c: Char) = c == ';'
    override fun tokenize(lexer: ILexer) = StatementSeparatorToken()
}