package org.pl.lexer;

public class LexerError {

    private final String message;
    private final int location;

    public LexerError(String message, int location) {
        this.message = message;
        this.location = location;
    }

    @Override
    public String toString() {
        return new StringBuilder("Lexer error at position ")
                .append(location)
                .append(": ")
                .append(message)
                .toString();
    }
}
