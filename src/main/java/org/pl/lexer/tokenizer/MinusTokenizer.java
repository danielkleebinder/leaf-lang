package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.arithmetic.MinusToken;

/**
 * Tokenizes the minus symbol '-'.
 */
public class MinusTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == '-';
    }

    @Override
    public MinusToken tokenize(ILexer lexer) throws TokenizerException {
        return new MinusToken();
    }
}
