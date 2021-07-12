package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.arithmetic.DivideToken;

/**
 * Tokenizes the symbol '/'.
 */
public class DivideTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == '/';
    }

    @Override
    public DivideToken tokenize(ILexer lexer) throws TokenizerException {
        return new DivideToken();
    }
}
