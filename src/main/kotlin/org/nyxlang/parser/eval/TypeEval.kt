package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.keyword.BoolKeywordToken
import org.nyxlang.lexer.token.keyword.NumberKeywordToken
import org.nyxlang.lexer.token.keyword.StringKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.TypeNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the type semantics:
 *
 * <type> ::= <number> | <bool> | <string>
 *
 */
class TypeEval(private val parser: IParser) : IEval {
    override fun eval() = when (parser.token::class) {
        NumberKeywordToken::class -> parser.advance { TypeNode("number") }
        BoolKeywordToken::class -> parser.advance { TypeNode("bool") }
        StringKeywordToken::class -> parser.advance { TypeNode("string") }
        NameToken::class -> TypeNode((parser.tokenAndAdvance as NameToken).getValue())
        else -> throw EvalException("Unknown type \"${parser.token}\"")
    }
}