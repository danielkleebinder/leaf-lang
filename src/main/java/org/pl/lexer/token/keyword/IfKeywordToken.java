package org.pl.lexer.token.keyword;

import org.pl.lexer.token.IToken;

/**
 * Identifies the "if" keyword.
 */
public class IfKeywordToken implements IToken {
    @Override
    public String toString() {
        return "IfKeywordToken{if}";
    }
}
