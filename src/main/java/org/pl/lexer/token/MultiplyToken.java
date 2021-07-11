package org.pl.lexer.token;

public class MultiplyToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Multiply token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "MultiplyToken{*}";
    }
}
