package org.pl.lexer.token;


/**
 * Value tokens are tokens that hold an additional value.
 *
 * @param <T> Value type.
 */
public interface IValueToken<T> extends IToken {

    /**
     * Returns the value of the token.
     *
     * @return Value.
     */
    T getValue();
}
