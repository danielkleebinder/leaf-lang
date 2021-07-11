package org.pl.lexer.token;

public class LeftBracketToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Left bracket token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "LeftBracketToken{[}";
    }
}
