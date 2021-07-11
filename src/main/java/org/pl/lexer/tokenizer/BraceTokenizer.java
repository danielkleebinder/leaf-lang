package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.token.LeftBraceToken;
import org.pl.lexer.token.RightBraceToken;

public class BraceTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == '{' || c == '}';
    }

    @Override
    public TokenizerResult tokenize(ILexer lexer) {
        if (lexer.getSymbol() == '{') {
            return new TokenizerResult(new LeftBraceToken());
        }
        return new TokenizerResult(new RightBraceToken());
    }
}
