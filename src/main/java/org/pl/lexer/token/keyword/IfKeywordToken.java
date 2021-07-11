package org.pl.lexer.token.keyword;

import org.pl.lexer.token.IToken;

public class IfKeywordToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("If keyword token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "IfKeywordToken{if}";
    }
}
