package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.AssignToken;
import org.pl.lexer.token.IToken;
import org.pl.lexer.token.logical.*;


/**
 * Tokenizes logical symbols like '<', '<=' or '&&'.
 */
public class LogicalTokenizer implements ITokenizer {

    @Override
    public boolean matches(Character c) {
        return c == '&' || c == '|' || c == '!' ||
                c == '>' || c == '<' || c == '=';
    }

    @Override
    public IToken tokenize(ILexer lexer) throws TokenizerException {
        if (lexer.getSymbol() == '&' && lexer.peekNextSymbol() == '&') {
            lexer.advanceCursor();
            return new LogicalAndToken();
        }
        if (lexer.getSymbol() == '|' && lexer.peekNextSymbol() == '|') {
            lexer.advanceCursor();
            return new LogicalOrToken();
        }
        if (lexer.getSymbol() == '>') {
            if (lexer.peekNextSymbol() == '=') {
                lexer.advanceCursor();
                return new GreaterThanOrEqualToken();
            }
            return new GreaterThanToken();
        }
        if (lexer.getSymbol() == '<') {
            if (lexer.peekNextSymbol() == '=') {
                lexer.advanceCursor();
                return new LessThanOrEqualToken();
            }
            return new LessThanToken();
        }
        if (lexer.getSymbol() == '=') {
            if (lexer.peekNextSymbol() == '=') {
                lexer.advanceCursor();
                return new EqualToken();
            }
            return new AssignToken();
        }
        if (lexer.getSymbol() == '!') {
            if (lexer.peekNextSymbol() == '=') {
                lexer.advanceCursor();
                return new NotEqualToken();
            }
            return new LogicalNotToken();
        }

        throw new TokenizerException("Unknown logical symbol " + lexer.getSymbol(), lexer.getCursorPosition());
    }
}
