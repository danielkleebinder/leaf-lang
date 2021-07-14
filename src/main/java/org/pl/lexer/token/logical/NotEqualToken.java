package org.pl.lexer.token.logical;

import org.pl.lexer.token.IToken;

/**
 * Identifies the logical NOT EQUAL operator.
 */
public class NotEqualToken implements IToken {
    @Override
    public String toString() {
        return "NotEqualToken{!=}";
    }
}
