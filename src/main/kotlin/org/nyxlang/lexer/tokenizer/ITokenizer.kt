package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.exception.TokenizerException
import org.nyxlang.lexer.token.IToken

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
     * Performs lexical analysis on the next symbols in the input buffer.
     *
     * @param lexer Lexer.
     * @return Tokenizer result.
     * @throws TokenizerException May occur if lexical analysis fails for a certain token.
     */
    fun tokenize(lexer: ILexer): IToken
}