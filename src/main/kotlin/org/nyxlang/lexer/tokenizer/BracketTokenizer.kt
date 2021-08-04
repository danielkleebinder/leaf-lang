package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.source.ISource
import org.nyxlang.lexer.source.advance
import org.nyxlang.lexer.token.ITokenFactory
import org.nyxlang.lexer.token.Token
import org.nyxlang.lexer.token.TokenType
import org.nyxlang.error.ErrorCode

/**
 * Tokenizes parenthesis '()', curly braces '{}' and brackets '[]'.
 */
class BracketTokenizer : ITokenizer {

    override fun matches(c: Char) = c == '(' || c == ')' ||
            c == '{' || c == '}' ||
            c == '[' || c == ']'

    override fun tokenize(source: ISource, tokenFactory: ITokenFactory): Token {
        val currentSymbol = source.symbol
        return source.advance {
            when (currentSymbol) {
                '(' -> tokenFactory.newToken(TokenType.LEFT_PARENTHESIS)
                ')' -> tokenFactory.newToken(TokenType.RIGHT_PARENTHESIS)
                '{' -> tokenFactory.newToken(TokenType.LEFT_CURLY_BRACE)
                '}' -> tokenFactory.newToken(TokenType.RIGHT_CURLY_BRACE)
                '[' -> tokenFactory.newToken(TokenType.LEFT_BRACKET)
                ']' -> tokenFactory.newToken(TokenType.RIGHT_BRACKET)
                else -> tokenFactory.newToken(TokenType.ERROR, ErrorCode.UNEXPECTED_BRACKET_TOKEN)
            }
        }
    }
}