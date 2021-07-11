package org.pl.lexer.tokenizer;

import org.pl.lexer.LexerError;
import org.pl.lexer.token.IToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenizerResult {
    public IToken token;
    public final List<LexerError> errors = new ArrayList<>(8);

    public TokenizerResult(IToken token, LexerError... errors) {
        this.token = token;
        this.errors.addAll(Arrays.asList(errors));
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }
}
