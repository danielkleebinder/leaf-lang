package org.pl.lexer.token;

public class LeftBraceToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Left brace token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "LeftBraceToken{{}";
    }
}
