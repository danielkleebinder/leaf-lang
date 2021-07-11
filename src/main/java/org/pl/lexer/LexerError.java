package org.pl.lexer;

public class LexerError {
    private final int location;
    private final String message;

    public LexerError(int location, String message) {
        this.location = location;
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringBuilder("Lexer failed at position ")
                .append(location)
                .append(": ")
                .append(message)
                .toString();
    }
}
