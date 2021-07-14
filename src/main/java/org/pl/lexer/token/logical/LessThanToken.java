package org.pl.lexer.token.logical;

import org.pl.lexer.token.IToken;

/**
 * Identifies the less than operator.
 */
public class LessThanToken implements IToken {
    @Override
    public String toString() {
        return "LessThanToken{<}";
    }
}
