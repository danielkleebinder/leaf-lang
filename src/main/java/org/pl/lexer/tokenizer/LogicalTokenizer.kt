package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.exception.TokenizerException
import org.pl.lexer.token.AssignToken
import org.pl.lexer.token.IToken
import org.pl.lexer.token.logical.*

/**
 * Tokenizes logical symbols like '<', '<=' or '&&'.
 */
class LogicalTokenizer : ITokenizer {

    override fun matches(c: Char) = c == '&' || c == '|' ||
            c == '!' || c == '>' || c == '<' ||
            c == '='

    override fun tokenize(lexer: ILexer): IToken {
        if (lexer.symbol == '&' && lexer.peekNextSymbol == '&') {
            lexer.advanceCursor()
            return LogicalAndToken()
        }
        if (lexer.symbol == '|' && lexer.peekNextSymbol == '|') {
            lexer.advanceCursor()
            return LogicalOrToken()
        }
        if (lexer.symbol == '>') {
            if (lexer.peekNextSymbol == '=') {
                lexer.advanceCursor()
                return GreaterThanOrEqualToken()
            }
            return GreaterThanToken()
        }
        if (lexer.symbol == '<') {
            if (lexer.peekNextSymbol == '=') {
                lexer.advanceCursor()
                return LessThanOrEqualToken()
            }
            return LessThanToken()
        }
        if (lexer.symbol == '=') {
            if (lexer.peekNextSymbol == '=') {
                lexer.advanceCursor()
                return EqualToken()
            }
            return AssignToken()
        }
        if (lexer.symbol == '!') {
            if (lexer.peekNextSymbol == '=') {
                lexer.advanceCursor()
                return NotEqualToken()
            }
            return LogicalNotToken()
        }
        throw TokenizerException("Unknown logical symbol " + lexer.symbol, lexer.cursorPosition)
    }
}