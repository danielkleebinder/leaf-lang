package org.pl.lexer.token.logical;

import org.pl.lexer.token.IToken;

public class NotEqualToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Not equal token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "NotEqualToken{!=}";
    }
}
