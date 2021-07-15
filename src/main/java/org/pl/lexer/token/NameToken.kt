package org.pl.lexer.token

/**
 * Represents keywords, variable and class names, etc.
 */
class NameToken(private val name: String) : IValueToken<String> {
    override fun getValue() = name
    override fun toString() = "NameToken{name=$name}"
}