package org.pl.lexer.token;

import java.math.BigDecimal;

/**
 * Float tokens represent floating point numbers.
 */
public class NumberToken implements IValueToken<BigDecimal> {

    private final BigDecimal number;

    public NumberToken(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getValue() {
        return number;
    }

    @Override
    public String toString() {
        return "NumberToken{val=" + number + '}';
    }
}
