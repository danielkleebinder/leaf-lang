package org.nyxlang.lexer.token

/**
 * Value tokens are tokens that hold an additional value.
 *
 * @param <T> Value type.
 */
interface IValueToken<T> : IToken {

    /**
     * The immutable value of the token.
     */
    val value: T
}