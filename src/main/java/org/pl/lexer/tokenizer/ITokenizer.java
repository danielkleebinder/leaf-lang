package org.pl.lexer.tokenizer;

import org.pl.lexer.ILexer;


/**
 * Generates a token from a given sequence of input characters.
 */
public interface ITokenizer {

    /**
     * Tests if this tokenizer is applicable for the next character in the buffer.
     *
     * @param c Next character in the input buffer.
     * @return True if applicable, otherwise false.
     */
    boolean matches(Character c);

    /**
     * Performs lexical analysis on the next symbols in the input buffer.
     *
     * @param lexer Lexer.
     * @return Tokenizer result.
     */
    TokenizerResult tokenize(ILexer lexer);
}
