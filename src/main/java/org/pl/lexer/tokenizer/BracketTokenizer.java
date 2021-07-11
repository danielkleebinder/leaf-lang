package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.token.LeftBracketToken;
import org.pl.lexer.token.RightBracketToken;

public class BracketTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == '[' || c == ']';
    }

    @Override
    public TokenizerResult tokenize(ILexer lexer) {
        if (lexer.getSymbol() == '[') {
            return new TokenizerResult(new LeftBracketToken());
        }
        return new TokenizerResult(new RightBracketToken());
    }
}
