package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.CommaToken;

/**
 * Tokenizes the symbol ','.
 */
public class CommaTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == ',';
    }

    @Override
    public CommaToken tokenize(ILexer lexer) throws TokenizerException {
        return new CommaToken();
    }
}
