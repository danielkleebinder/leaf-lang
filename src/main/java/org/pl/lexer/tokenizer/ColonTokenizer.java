package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;
import org.pl.lexer.exception.TokenizerException;
import org.pl.lexer.token.ColonToken;


/**
 * Tokenizes the symbol ':'.
 */
public class ColonTokenizer implements ITokenizer {
    @Override
    public boolean matches(Character c) {
        return c == ':';
    }

    @Override
    public ColonToken tokenize(ILexer lexer) throws TokenizerException {
        return new ColonToken();
    }
}
