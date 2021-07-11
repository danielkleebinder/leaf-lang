package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.token.ColonToken;
import org.pl.lexer.token.LeftBraceToken;
import org.pl.lexer.token.RightBraceToken;

public class ColonTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == ':';
    }

    @Override
    public TokenizerResult tokenize(ILexer lexer) {
        return new TokenizerResult(new ColonToken());
    }
}
