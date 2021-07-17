package org.nyxlang.lexer.token

/**
 * Value tokens are tokens that hold an additional value.
 *
 * @param <T> Value type.
 */
interface IValueToken<T> : IToken {

    /**
     * Returns the value of the token.
     *
     * @return Value.
     */
    fun getValue(): T
}