package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.token.logical.*;

public class LogicalTokenizer implements ITokenizer {

    @Override
    public boolean matches(Character c) {
        return c == '&' || c == '|' || c == '!' ||
                c == '>' || c == '<' || c == '=';
    }

    @Override
    public TokenizerResult tokenize(ILexer lexer) {
        if (lexer.getSymbol() == '&' && lexer.peekNextSymbol() == '&') {
            lexer.advanceCursor();
            return new TokenizerResult(new LogicalAndToken());
        }
        if (lexer.getSymbol() == '|' && lexer.peekNextSymbol() == '|') {
            lexer.advanceCursor();
            return new TokenizerResult(new LogicalOrToken());
        }
        if (lexer.getSymbol() == '>') {
            if (lexer.peekNextSymbol() == '=') {
                lexer.advanceCursor();
                return new TokenizerResult(new GreaterThanOrEqualToken());
            }
            return new TokenizerResult(new GreaterThanToken());
        }
        if (lexer.getSymbol() == '<') {
            if (lexer.peekNextSymbol() == '=') {
                lexer.advanceCursor();
                return new TokenizerResult(new LessThanOrEqualToken());
            }
            return new TokenizerResult(new LessThanToken());
        }
        if (lexer.getSymbol() == '=' && lexer.peekNextSymbol() == '=') {
            lexer.advanceCursor();
            return new TokenizerResult(new EqualToken());
        }
        if (lexer.getSymbol() == '!') {
            if (lexer.peekNextSymbol() == '=') {
                lexer.advanceCursor();
                return new TokenizerResult(new NotEqualToken());
            }
            return new TokenizerResult(new LogicalNotToken());
        }
        return new TokenizerResult(null);
    }
}
