package org.nyxlang.lexer.token

import java.math.BigDecimal

/**
 * Float tokens represent floating point numbers.
 */
class NumberToken(override val value: BigDecimal) : IValueToken<BigDecimal> {
    override fun toString() = "NumberToken(val=$value)"
}