package org.pl.lexer.exception;

/**
 * May occur during the lexical analysis.
 */
public class TokenizerException extends Exception {

    private int location;

    public TokenizerException(String message, int location) {
        super(message);
        this.location = location;
    }

    /**
     * Returns the location in code where the error occurred.
     *
     * @return Code location.
     */
    public int getLocation() {
        return location;
    }
}
