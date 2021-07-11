package org.pl.lexer.token;

public class ColonToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Colon token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "ColonToken{:}";
    }
}
