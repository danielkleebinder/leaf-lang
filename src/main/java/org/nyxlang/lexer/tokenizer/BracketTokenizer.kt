package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.exception.TokenizerException
import org.nyxlang.lexer.token.bracket.*

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