package org.pl.lexer.token.logical;

import org.pl.lexer.token.IToken;

/**
 * Identifies the greater than or equal operator.
 */
public class GreaterThanOrEqualToken implements IToken {
    @Override
    public String toString() {
        return "GreaterThanOrEqualToken{>=}";
    }
}
