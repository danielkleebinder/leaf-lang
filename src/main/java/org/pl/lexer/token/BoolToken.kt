package org.pl.lexer.token

/**
 * Represents a boolean value.
 */
class BoolToken(private val value: Boolean) : IValueToken<Boolean> {
    override fun getValue() = value
    override fun toString() = "BoolToken{val=$value}"
}