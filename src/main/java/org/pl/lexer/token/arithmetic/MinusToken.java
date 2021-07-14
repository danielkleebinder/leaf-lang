package org.pl.lexer.token.arithmetic;

import org.pl.lexer.token.IToken;

/**
 * Identifies number subtraction.
 */
public class MinusToken implements IToken {
    @Override
    public String toString() {
        return "MinusToken{-}";
    }
}
