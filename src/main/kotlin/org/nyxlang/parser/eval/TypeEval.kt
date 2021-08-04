package org.nyxlang.parser.eval

import org.nyxlang.error.ErrorCode
import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.TypeNode

/**
 * Evaluates the type semantics:
 *
 * <type>  ::= <number> | <bool> | <string> | <fun>
 *
 */
class TypeEval(private val parser: IParser) : IEval {
    override fun eval() = when (parser.token.kind) {
        TokenType.KEYWORD_NUMBER -> parser.advance { TypeNode("number") }
        TokenType.KEYWORD_BOOL -> parser.advance { TypeNode("bool") }
        TokenType.KEYWORD_STRING -> parser.advance { TypeNode("string") }
        TokenType.KEYWORD_ARRAY -> parser.advance { TypeNode("array") }
        TokenType.KEYWORD_FUN -> parser.advance { TypeNode("function") }
        TokenType.IDENTIFIER -> TypeNode(parser.tokenAndAdvance.value as String)
        else -> {
            parser.flagError(ErrorCode.INVALID_TYPE_DECLARATION)
            TypeNode("<unknown>")
        }
    }
}