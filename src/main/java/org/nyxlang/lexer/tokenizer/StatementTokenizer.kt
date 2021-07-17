package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.StatementSeparatorToken

/**
 * Tokenizes the statement separation symbol ';' and '\n'.
 */
class StatementTokenizer : ITokenizer {
    override fun matches(c: Char) = c == ';' || c == '\n'
    override fun tokenize(lexer: ILexer) = StatementSeparatorToken()
}