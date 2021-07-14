package org.pl.lexer.token.keyword;

import org.pl.lexer.token.IToken;

/**
 * Identifies the "fun" keyword.
 */
public class FunctionKeywordToken implements IToken {
    @Override
    public String toString() {
        return "FunctionKeywordToken{fun}";
    }
}
