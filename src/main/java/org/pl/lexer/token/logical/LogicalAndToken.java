package org.pl.lexer.token.logical;

import org.pl.lexer.token.IToken;

/**
 * Identifies the logical AND operator.
 */
public class LogicalAndToken implements IToken {
    @Override
    public String toString() {
        return "AndToken{&&}";
    }
}
