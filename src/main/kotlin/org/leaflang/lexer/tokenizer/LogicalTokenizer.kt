package org.leaflang.lexer.tokenizer

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.source.advance
import org.leaflang.lexer.source.advanceIf
import org.leaflang.lexer.token.ITokenFactory
import org.leaflang.lexer.token.Token
import org.leaflang.lexer.token.TokenType
import org.leaflang.error.ErrorCode

/**
 * Tokenizes logical symbols like '<', '<=' or '&&'.
 */
class LogicalTokenizer : ITokenizer {

    override fun matches(c: Char) = c == '&' || c == '|' ||
            c == '!' || c == '>' || c == '<' ||
            c == '='

    override fun tokenize(source: ISource, tokenFactory: ITokenFactory): Token {
        val currentSymbol = source.symbol
        val nextSymbol = source.peekNextSymbol
        source.advance {
            source.advanceIf(currentSymbol == '&' && nextSymbol == '&') { return tokenFactory.newToken(TokenType.LOGICAL_AND) }
            source.advanceIf(currentSymbol == '|' && nextSymbol == '|') { return tokenFactory.newToken(TokenType.LOGICAL_OR) }
            source.advanceIf(currentSymbol == '<' && nextSymbol == '=') { return tokenFactory.newToken(TokenType.LESS_EQUALS) }
            source.advanceIf(currentSymbol == '<' && nextSymbol == '-') { return tokenFactory.newToken(TokenType.LEFT_ARROW) }
            source.advanceIf(currentSymbol == '>' && nextSymbol == '=') { return tokenFactory.newToken(TokenType.GREATER_EQUALS) }
            source.advanceIf(currentSymbol == '=' && nextSymbol == '=') { return tokenFactory.newToken(TokenType.EQUALS) }
            source.advanceIf(currentSymbol == '!' && nextSymbol == '=') { return tokenFactory.newToken(TokenType.NOT_EQUALS) }
            if (currentSymbol == '<') return tokenFactory.newToken(TokenType.LESS)
            if (currentSymbol == '>') return tokenFactory.newToken(TokenType.GREATER)
            if (currentSymbol == '=') return tokenFactory.newToken(TokenType.ASSIGNMENT)
            if (currentSymbol == '!') return tokenFactory.newToken(TokenType.LOGICAL_NOT)
            return tokenFactory.newToken(TokenType.ERROR, ErrorCode.UNEXPECTED_LOGICAL_TOKEN)
        }
    }
}