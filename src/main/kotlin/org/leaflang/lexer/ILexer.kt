package org.leaflang.lexer

import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.token.Token


/**
 * A lexer (or tokenizer) performs a lexical analysis on a given sequence of
 * characters. They are systematically converted to another alphabet which
 * represent abstract syntax tokens. Lexer will detect syntactic coding errors.
 */
interface ILexer {

    /**
     * Tokenizes the given [source] code.
     */
    fun tokenize(source: ISource): Array<Token>
}