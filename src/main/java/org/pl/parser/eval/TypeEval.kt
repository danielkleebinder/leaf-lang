package org.pl.parser.eval

import org.pl.lexer.token.NameToken
import org.pl.lexer.token.keyword.BoolKeywordToken
import org.pl.lexer.token.keyword.NumberKeywordToken
import org.pl.parser.IParser
import org.pl.parser.advance
import org.pl.parser.ast.TypeNode
import org.pl.parser.exception.EvalException

/**
 * Evaluates the type semantics:
 *
 * <type> ::= <number> | <bool>
 *
 */
class TypeEval(private val parser: IParser) : IEval {
    override fun eval() = when (parser.token::class) {
        NumberKeywordToken::class -> parser.advance { TypeNode("number") }
        BoolKeywordToken::class -> parser.advance { TypeNode("bool") }
        NameToken::class -> TypeNode((parser.tokenAndAdvance as NameToken).getValue())
        else -> throw EvalException("Unknown type \"${parser.token}\"")
    }
}