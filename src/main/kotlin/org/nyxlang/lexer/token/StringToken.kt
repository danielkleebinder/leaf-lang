package org.nyxlang.lexer.token

/**
 * Represents a string value.
 */
class StringToken(private val value: String) : IValueToken<String> {
    override fun getValue() = value
    override fun toString() = "StringToken{val=$value}"
}