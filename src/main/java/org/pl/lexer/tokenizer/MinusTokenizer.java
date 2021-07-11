package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.token.MinusToken;
import org.pl.lexer.token.PlusToken;

public class MinusTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == '-';
    }

    @Override
    public TokenizerResult tokenize(ILexer lexer) {
        return new TokenizerResult(new MinusToken());
    }
}
