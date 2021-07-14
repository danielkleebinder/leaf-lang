package org.pl.lexer.token.keyword;

import org.pl.lexer.token.IToken;

/**
 * Identifies the "loop" keyword.
 */
public class LoopKeywordToken implements IToken {
    @Override
    public String toString() {
        return "LoopKeywordToken{loop}";
    }
}
