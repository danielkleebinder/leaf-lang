package org.nyxlang.interpreter;

public class InterpreterError {

    private final String message;

    public InterpreterError(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringBuilder("Interpreter error: ")
                .append(message)
                .toString();
    }
}
