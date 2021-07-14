package org.pl.lexer.token.arithmetic;

import org.pl.lexer.token.IToken;


/**
 * Identifies number modulo operation.
 */
public class ModToken implements IToken {
    @Override
    public String toString() {
        return "ModToken{%}";
    }
}
