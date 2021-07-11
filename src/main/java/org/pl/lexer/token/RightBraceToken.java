package org.pl.lexer.token;

public class RightBraceToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Right brace token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "RightBraceToken{}}";
    }
}
