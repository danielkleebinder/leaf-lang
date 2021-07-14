package org.pl.lexer.token;

/**
 * Represents a boolean value.
 */
public class BoolToken implements IValueToken<Boolean> {

    private final Boolean value;

    public BoolToken(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BoolToken{val=" + value + '}';
    }
}
