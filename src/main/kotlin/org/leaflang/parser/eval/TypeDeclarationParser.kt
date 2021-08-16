package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.DeclarationsNode
import org.leaflang.parser.ast.TypeDeclareNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the custom type declaration semantics:
 *
 * <type-declaration> ::= 'type' (NL)* <name> (NL)*
 *                           '{' (NL)* (<declarations> (NL)*)* '}'
 *
 */
class TypeDeclarationParser(private val parser: ILeafParser,
                            private val parserFactory: IParserFactory) : IParser {

    private val varDeclarationsParser = parserFactory.varDeclarationsParser

    override fun parse(): TypeDeclareNode {
        var name = "<anonymous>"
        val fields = arrayListOf<DeclarationsNode>()

        typeName { name = parser.tokenAndAdvance.value as String }

        val wasNewLine = TokenType.NEW_LINE == parser.token.kind
        parser.skipNewLines()

        // Custom types do not need a body at all
        if (TokenType.LEFT_CURLY_BRACE == parser.token.kind) {
            typeBody {
                while (TokenType.RIGHT_CURLY_BRACE != parser.token.kind) {
                    fields.add(varDeclarationsParser.parse())
                    parser.skipNewLines()
                }
            }
        } else if (wasNewLine) {
            parser.advanceCursor(-1)
        }

        return TypeDeclareNode(name, fields.toList())
    }

    /**
     * Evaluates the type keyword and custom type name. Throws an exception if semantics are incorrect.
     */
    private inline fun typeName(fn: () -> Unit) {
        // Is this even a type declaration?
        if (TokenType.KEYWORD_TYPE != parser.token.kind) parser.flagError(ErrorCode.MISSING_KEYWORD_TYPE)
        parser.advanceCursor()
        parser.skipNewLines()

        if (TokenType.IDENTIFIER != parser.token.kind) {
            parser.flagError(ErrorCode.MISSING_IDENTIFIER)
        } else {
            fn()
        }
    }

    /**
     * Evaluates the custom type body. Throws an exception if semantics are incorrect.
     */
    private inline fun typeBody(fn: () -> Unit) {
        if (TokenType.LEFT_CURLY_BRACE != parser.token.kind) parser.flagError(ErrorCode.MISSING_TYPE_LEFT_CURLY_BRACE)
        parser.advanceCursor()
        parser.skipNewLines()

        // Do we have declarations at all?
        if (TokenType.RIGHT_CURLY_BRACE != parser.token.kind) {
            fn()
            parser.skipNewLines()
        }

        if (TokenType.RIGHT_CURLY_BRACE != parser.token.kind) parser.flagError(ErrorCode.MISSING_TYPE_RIGHT_CURLY_BRACE)
        parser.advanceCursor()
    }
}