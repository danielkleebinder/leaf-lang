package org.pl.lexer.token.arithmetic;

import org.pl.lexer.token.IToken;

/**
 * Identifies number addition.
 */
public class PlusToken implements IToken {
    @Override
    public String toString() {
        return "PlusToken{+}";
    }
}
