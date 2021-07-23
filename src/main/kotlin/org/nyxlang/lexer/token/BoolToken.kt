package org.nyxlang.lexer.token

/**
 * Represents a boolean value.
 */
class BoolToken(override val value: Boolean) : IValueToken<Boolean> {
    override fun toString() = "BoolToken(val=$value)"
}