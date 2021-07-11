package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.token.DivideToken;

public class DivideTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == '/';
    }

    @Override
    public TokenizerResult tokenize(ILexer lexer) {
        return new TokenizerResult(new DivideToken());
    }
}
