package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.keyword.*
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.TypeNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the type semantics:
 *
 * <type>  ::= <number> | <bool> | <string> | <fun>
 *
 */
class TypeEval(private val parser: IParser) : IEval {
    override fun eval() = when (parser.token::class) {
        NumberKeywordToken::class -> parser.advance { TypeNode("number") }
        BoolKeywordToken::class -> parser.advance { TypeNode("bool") }
        StringKeywordToken::class -> parser.advance { TypeNode("string") }
        ArrayKeywordToken::class -> parser.advance { TypeNode("array") }
        FunKeywordToken::class -> parser.advance { TypeNode("function") }
        NameToken::class -> TypeNode((parser.tokenAndAdvance as NameToken).value)
        else -> throw EvalException("Unknown type \"${parser.token}\"")
    }
}