package org.pl.lexer.token;

public class DivideToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Divide token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "DivideToken{/}";
    }
}
