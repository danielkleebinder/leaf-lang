package org.pl.lexer.token;

public class CommaToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Comma token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "CommaToken{,}";
    }
}
