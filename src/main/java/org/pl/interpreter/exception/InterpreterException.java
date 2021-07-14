package org.pl.interpreter.exception;

import org.pl.interpreter.InterpreterError;

import java.util.List;

/**
 * An interpreter throws this exception if any errors occurred during runtime.
 */
public class InterpreterException extends RuntimeException {

    private List<InterpreterError> errors;

    public InterpreterException(String message, List<InterpreterError> errors) {
        super(message);
        this.errors = errors;
    }

    public List<InterpreterError> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        var result = new StringBuilder(128);
        result.append(InterpreterException.class);
        result.append(": ");
        result.append(getMessage());
        for (InterpreterError error : errors) {
            result.append("\n - ");
            result.append(error);
        }
        result.append("\n");
        return result.toString();
    }
}
