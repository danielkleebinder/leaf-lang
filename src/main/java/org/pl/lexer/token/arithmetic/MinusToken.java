package org.pl.lexer.token.arithmetic;

import org.pl.lexer.token.IToken;

public class MinusToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Minus token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "MinusToken{-}";
    }
}
