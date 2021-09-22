package org.leaflang.parser.syntax

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advance
import org.leaflang.parser.ast.type.TypeNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the type semantics:
 *
 * <type>  ::= <number> | <bool> | <string> | <fun>
 *
 */
class TypeParser(private val parser: ILeafParser,
                 private val parserFactory: IParserFactory) : IParser {
    override fun parse() = when (parser.token.kind) {
        TokenType.KEYWORD_NUMBER -> parser.advance { TypeNode(parser.nodePosition(), "number") }
        TokenType.KEYWORD_BOOL -> parser.advance { TypeNode(parser.nodePosition(), "bool") }
        TokenType.KEYWORD_STRING -> parser.advance { TypeNode(parser.nodePosition(), "string") }
        TokenType.KEYWORD_ARRAY -> parser.advance { TypeNode(parser.nodePosition(), "array") }
        TokenType.KEYWORD_FUN -> parser.advance { TypeNode(parser.nodePosition(), "function") }
        TokenType.IDENTIFIER -> TypeNode(parser.nodePosition(), parser.tokenAndAdvance.value as String)
        else -> {
            parser.flagError(ErrorCode.INVALID_TYPE_DECLARATION)
            var unknownTypeName = "<unknown"
            if (parser.token.value != null) unknownTypeName += ("::" + parser.token.value)
            unknownTypeName += ">"
            TypeNode(parser.nodePosition(), unknownTypeName)
        }
    }
}