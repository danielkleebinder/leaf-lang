package org.pl.lexer.token;

public class RightParenthesisToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Right parenthesis token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "RightParenthesisToken{)}";
    }
}
