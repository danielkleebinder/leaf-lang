package org.pl.lexer.token.logical;

import org.pl.lexer.token.IToken;

/**
 * Identifies the logical OR operator.
 */
public class LogicalOrToken implements IToken {
    @Override
    public String toString() {
        return "LogicalOrToken{||}";
    }
}
