package org.pl.lexer.token.arithmetic;

import org.pl.lexer.token.IToken;

public class PlusToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Plus token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "PlusToken{+}";
    }
}
