package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.source.ISource
import org.nyxlang.lexer.token.ITokenFactory
import org.nyxlang.lexer.token.Token

/**
 * Generates a token from a given sequence of input characters.
 */
interface ITokenizer {

    /**
     * Tests if this tokenizer is applicable for the next character in the buffer.
     *
     * @param c Next character in the input buffer.
     * @return True if applicable, otherwise false.
     */
    fun matches(c: Char): Boolean

    /**
     * Performs lexical analysis on the next symbols from the given source.
     *
     * @param source Source.
     * @return Tokenizer result.
     */
    fun tokenize(source: ISource, tokenFactory: ITokenFactory): Token
}