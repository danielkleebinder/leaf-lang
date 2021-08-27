package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.DeclarationsNode
import org.leaflang.parser.ast.type.TypeDeclareNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the custom type declaration semantics:
 *
 * <type-declaration>  ::= 'type' (NL)* <name> (NL)*
 *                         (<trait-list>)?
 *                         (<type-body>)?
 *
 * <trait-list> ::= ':' (NL)* <name> (NL)* (',' (NL)* <name> (NL)*)*
 *
 */
class TypeDeclareParser(private val parser: ILeafParser,
                        private val parserFactory: IParserFactory) : IParser {

    private val varDeclarationsParser = parserFactory.varDeclarationsParser

    override fun parse(): TypeDeclareNode {
        var name = "<anonymous>"
        val pos = parser.nodePosition()
        val traits = arrayListOf<String>()
        val fields = arrayListOf<DeclarationsNode>()

        typeName { name = parser.tokenAndAdvance.value as String }
        typeTraits {
            traits.add(parser.tokenAndAdvance.value as String)
            while (TokenType.COMMA == parser.token.kind) {
                parser.advanceCursor()
                parser.skipNewLines()

                if (TokenType.IDENTIFIER != parser.token.kind) {
                    parser.flagError(ErrorCode.MISSING_TRAIT_IDENTIFIER)
                    break
                } else {
                    traits.add(parser.tokenAndAdvance.value as String)
                }
            }
        }

        val wasNewLine = TokenType.NEW_LINE == parser.token.kind
        if (wasNewLine) parser.skipNewLines()

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

        return TypeDeclareNode(
                name = name,
                traits = traits.toList(),
                fields = fields.toList(),
                position = pos)
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
     * Evaluates the list of traits implemented by this type (if available).
     */
    private inline fun typeTraits(fn: () -> Unit) {
        if (TokenType.COLON != parser.token.kind) return
        parser.advanceCursor()
        parser.skipNewLines()

        // The first thing after the colon has to be an identifier
        if (TokenType.IDENTIFIER != parser.token.kind) parser.flagError(ErrorCode.MISSING_TRAIT_IDENTIFIER)
        fn()
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