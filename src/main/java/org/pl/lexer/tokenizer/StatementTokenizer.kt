package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.StatementSeparatorToken

/**
 * Tokenizes the statement separation symbol ';'.
 */
class StatementTokenizer : ITokenizer {
    override fun matches(c: Char) = c == ';'
    override fun tokenize(lexer: ILexer) = StatementSeparatorToken()
}