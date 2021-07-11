package org.pl.lexer.token;

/**
 * Represents a boolean value.
 */
public class BoolToken implements IToken<Boolean> {

    private final Boolean value;

    public BoolToken(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public boolean hasValue() {
        return true;
    }

    @Override
    public String toString() {
        return "BoolToken{val=" + value + '}';
    }
}
