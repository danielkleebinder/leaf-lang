package org.nyxlang.lexer.token

import java.math.BigDecimal

/**
 * Float tokens represent floating point numbers.
 */
class NumberToken(private val number: BigDecimal) : IValueToken<BigDecimal> {
    override fun getValue() = number;
    override fun toString() = "NumberToken{val=$number}"
}