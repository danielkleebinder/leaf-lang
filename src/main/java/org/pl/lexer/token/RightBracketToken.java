package org.pl.lexer.token;

public class RightBracketToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Right backet token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "RightBracketToken{]}";
    }
}
