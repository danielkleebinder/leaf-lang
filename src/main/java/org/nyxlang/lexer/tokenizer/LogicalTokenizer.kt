package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.exception.TokenizerException
import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.IToken
import org.nyxlang.lexer.token.logical.*

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