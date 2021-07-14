package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.arithmetic.ModToken;

/**
 * Tokenizes the minus symbol '%'.
 */
public class ModTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == '%';
    }

    @Override
    public ModToken tokenize(ILexer lexer) throws TokenizerException {
        return new ModToken();
    }
}
