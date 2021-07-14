package org.pl.parser.exception;

import org.pl.parser.ParserError;

import java.util.List;

/**
 * A parser throws this exception if any errors occurred during token analysis.
 */
public class ParserException extends RuntimeException {

    private List<ParserError> errors;

    public ParserException(String message, List<ParserError> errors) {
        super(message);
        this.errors = errors;
    }

    public List<ParserError> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        var result = new StringBuilder(128);
        result.append(ParserException.class);
        result.append(": ");
        result.append(getMessage());
        result.append("\n");
        for (ParserError error : errors) {
            result.append(" - ");
            result.append(error);
        }
        result.append("\n");
        return result.toString();
    }
}
