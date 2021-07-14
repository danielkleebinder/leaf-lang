package org.pl.lexer.token.keyword;

import org.pl.lexer.token.IToken;

/**
 * Identifies the "var" keyword.
 */
public class VarKeywordToken implements IToken {
    @Override
    public String toString() {
        return "VarKeywordToken{var}";
    }
}
