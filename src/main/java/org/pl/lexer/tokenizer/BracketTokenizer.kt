package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.LexerError
import org.pl.lexer.exception.TokenizerException
import org.pl.lexer.token.bracket.*

/**
 * Tokenizes parenthesis '()', curly braces '{}' and brackets '[]'.
 */
class BracketTokenizer : ITokenizer {

    override fun matches(c: Char) = c == '(' || c == ')' ||
            c == '{' || c == '}' ||
            c == '[' || c == ']'

    override fun tokenize(lexer: ILexer) = when (lexer.symbol) {
        '(' -> LeftParenthesisToken()
        ')' -> RightParenthesisToken()
        '{' -> LeftCurlyBraceToken()
        '}' -> RightCurlyBraceToken()
        '[' -> LeftBracketToken()
        ']' -> RightBracketToken()
        else -> throw TokenizerException("Unknown bracket symbol " + lexer.symbol, lexer.cursorPosition)
    }
}