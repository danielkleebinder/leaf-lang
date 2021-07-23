package org.nyxlang.lexer.token

/**
 * Represents a string value.
 */
class StringToken(override val value: String) : IValueToken<String> {
    override fun toString() = "StringToken(val=$value)"
}