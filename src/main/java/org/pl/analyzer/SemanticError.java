package org.pl.analyzer;

public class SemanticError {

    private final String message;

    public SemanticError(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringBuilder("Semantic error: ")
                .append(message)
                .toString();
    }
}
