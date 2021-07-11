package org.pl.lexer.token;

public class AssignToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Assign token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "AssignToken{=}";
    }
}
