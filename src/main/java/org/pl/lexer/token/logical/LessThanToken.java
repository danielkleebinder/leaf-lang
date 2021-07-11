package org.pl.lexer.token.logical;

import org.pl.lexer.token.IToken;

public class LessThanToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Less than token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "LessThanToken{<}";
    }
}
