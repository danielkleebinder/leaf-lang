package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.IToken;
import org.pl.lexer.token.arithmetic.MultiplyToken;

/**
 * Tokenizes the multiply symbol '*'.
 */
public class MultiplyTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == '*';
    }

    @Override
    public MultiplyToken tokenize(ILexer lexer) throws TokenizerException {
        return new MultiplyToken();
    }
}
