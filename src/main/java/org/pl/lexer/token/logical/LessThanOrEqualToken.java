package org.pl.lexer.token.logical;

import org.pl.lexer.token.IToken;

/**
 * Identifies the less than or equal operator.
 */
public class LessThanOrEqualToken implements IToken {
    @Override
    public String toString() {
        return "LessThanOrEqualToken{<=}";
    }
}
