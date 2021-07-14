package org.pl.lexer.token.arithmetic;

import org.pl.lexer.token.IToken;

/**
 * Identifies number multiplication.
 */
public class MultiplyToken implements IToken {
    @Override
    public String toString() {
        return "MultiplyToken{*}";
    }
}
