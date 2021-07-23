package org.nyxlang.lexer.token

/**
 * Represents keywords, variable and class names, etc.
 */
class NameToken(override val value: String) : IValueToken<String> {
    override fun toString() = "NameToken(name=$value)"
}