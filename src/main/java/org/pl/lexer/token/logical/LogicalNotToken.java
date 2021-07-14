package org.pl.lexer.token.logical;

import org.pl.lexer.token.IToken;

/**
 * Identifies the logical NOT operator.
 */
public class LogicalNotToken implements IToken {
    @Override
    public String toString() {
        return "NotToken{!}";
    }
}
