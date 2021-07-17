package org.nyxlang.lexer

import org.nyxlang.lexer.token.IToken


/**
 * A lexer (or tokenizer) performs a lexical analysis on a given sequence of
 * characters. They are systematically converted to another alphabet which
 * represent abstract syntax tokens. Lexer will detect syntactic coding errors.
 */
interface ILexer {

    /**
     * Tokenizes the given [program] code.
     */
    fun tokenize(program: String): Array<IToken>

    /**
     * Checks if the end of the program has been reached.
     */
    val isEndOfProgram: Boolean

    /**
     * Advances the cursor by one.
     */
    fun advanceCursor() = advanceCursor(1)

    /**
     * Advances the cursor by the given amount [by].
     */
    fun advanceCursor(by: Int): Int

    /**
     * Returns the current cursor position.
     */
    val cursorPosition: Int

    /**
     * Returns the current symbol.
     */
    val symbol: Char

    /**
     * Peeks the next symbol.
     */
    val peekNextSymbol: Char

    /**
     * Returns the entire program code.
     */
    val programCode: String?
}