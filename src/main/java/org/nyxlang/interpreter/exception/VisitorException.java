package org.nyxlang.interpreter.exception;

/**
 * Visitors may throw a visitor exception if interpretation is not possible.
 */
public class VisitorException extends Exception {
    public VisitorException() {
    }

    public VisitorException(String message) {
        super(message);
    }
}
