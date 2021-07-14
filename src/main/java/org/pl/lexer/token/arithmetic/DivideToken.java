package org.pl.lexer.token.arithmetic;

import org.pl.lexer.token.IToken;


/**
 * Identifies number division.
 */
public class DivideToken implements IToken {
    @Override
    public String toString() {
        return "DivideToken{/}";
    }
}
