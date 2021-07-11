package org.pl.lexer.token;

public class ComplementToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Complement token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "ComplementToken{~}";
    }
}
