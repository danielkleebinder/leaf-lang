package org.pl.lexer.token.keyword;

import org.pl.lexer.token.IToken;

/**
 * Identifies the "break" keyword.
 */
public class BreakKeywordToken implements IToken {
    @Override
    public String toString() {
        return "BreakKeywordToken{break}";
    }
}
