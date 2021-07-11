package org.pl.lexer.token;

/**
 * The token that separates statements.
 */
public class StatementSeparatorToken implements IToken {
    public Object getValue() {
        throw new UnsupportedOperationException("Statement separator token has no value");
    }

    public boolean hasValue() {
        return false;
    }

    @Override
    public String toString() {
        return "StatementSeparatorToken{;}";
    }
}
