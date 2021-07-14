package org.pl.lexer.token.keyword;

import org.pl.lexer.token.IToken;

/**
 * Identifies the "const" keyword.
 */
public class ConstKeywordToken implements IToken {
    @Override
    public String toString() {
        return "ConstKeywordToken{const}";
    }
}
