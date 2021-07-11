package org.pl.lexer.token;

public class LeftParenthesisToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Left parenthesis token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "LeftParenthesisToken{(}";
    }
}
