package org.pl.lexer.token.logical;

import org.pl.lexer.token.IToken;

/**
 * Identifies the equality operator.
 */
public class EqualToken implements IToken {
    @Override
    public String toString() {
        return "EqualToken{==}";
    }
}
