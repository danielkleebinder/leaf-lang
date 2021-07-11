package org.pl.lexer.token;


/**
 * Tokens are a programming language specific alphabet that are used
 * add meaning to program text.
 *
 * @param <T> Value type.
 */
public interface IToken<T> {

    /**
     * Returns the value of the token.
     *
     * @return Value.
     */
    T getValue();

    /**
     * Checks if this token has a values associated with it.
     *
     * @return True if a value is available, otherwise false.
     */
    boolean hasValue();
}
