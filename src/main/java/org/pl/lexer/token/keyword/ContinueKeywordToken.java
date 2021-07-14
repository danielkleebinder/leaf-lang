package org.pl.lexer.token.keyword;

import org.pl.lexer.token.IToken;

/**
 * Identifies the "continue" keyword.
 */
public class ContinueKeywordToken implements IToken {
    @Override
    public String toString() {
        return "ContinueKeywordToken{continue}";
    }
}
