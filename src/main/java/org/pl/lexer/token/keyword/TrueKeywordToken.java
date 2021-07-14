package org.pl.lexer.token.keyword;

import org.pl.lexer.token.IToken;

/**
 * Identifies the "true" keyword.
 */
public class TrueKeywordToken implements IToken {
    @Override
    public String toString() {
        return "TrueKeywordToken{true}";
    }
}
