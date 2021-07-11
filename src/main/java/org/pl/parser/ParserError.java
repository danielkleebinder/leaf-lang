package org.pl.parser;

public class ParserError {
    private final String message;

    public ParserError(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringBuilder("Parser failed: ")
                .append(message)
                .toString();
    }
}
