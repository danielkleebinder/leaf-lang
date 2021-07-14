package org.pl.lexer.exception;


import org.pl.lexer.LexerError;

import java.util.List;

/**
 * A lexer throws this exception if any errors occurred during lexical analysis.
 */
public class LexerException extends RuntimeException {

    private List<LexerError> errors;

    public LexerException(String message, List<LexerError> errors) {
        super(message);
        this.errors = errors;
    }

    public List<LexerError> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        var result = new StringBuilder(128);
        result.append(LexerException.class);
        result.append(": ");
        result.append(getMessage());
        for (LexerError error : errors) {
            result.append("\n - ");
            result.append(error);
        }
        result.append("\n");
        return result.toString();
    }
}
