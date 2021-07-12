package org.pl.interpreter.exception;

/**
 * Visitors may throw a visitor exception if interpretation is not possible.
 */
public class VisitorException extends Exception {
    public VisitorException() {
    }

    public VisitorException(String message) {
        super(message);
    }

    public VisitorException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisitorException(Throwable cause) {
        super(cause);
    }
}
