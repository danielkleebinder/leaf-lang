package org.pl.lexer.token.keyword;

import org.pl.lexer.token.IToken;

public class ElseKeywordToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Else keyword token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "ElseKeywordToken{else}";
    }
}
