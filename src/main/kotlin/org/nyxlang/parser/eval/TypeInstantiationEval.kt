package org.nyxlang.parser.eval

import org.nyxlang.error.ErrorCode
import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.TypeArgument
import org.nyxlang.parser.ast.TypeInstantiationNode

/**
 * Evaluates the type syntax:
 *
 * <type-inst>  ::= 'new' <name> ('{' (NL)* <inst-body> (NL)* '}')?
 * <inst-body>  ::= (<inst-value> (NL)* (',' (NL)* <inst-value> (NL)*)* )?
 * <inst-value> ::= (<name> '=')? <expr>
 *
 */
class TypeInstantiationEval(private val parser: IParser) : IEval {

    override fun eval(): TypeInstantiationNode {
        if (TokenType.KEYWORD_NEW != parser.token.kind) parser.flagError(ErrorCode.MISSING_KEYWORD_NEW)
        parser.advanceCursor()

        if (TokenType.IDENTIFIER != parser.token.kind) parser.flagError(ErrorCode.MISSING_TYPE_IDENTIFIER)
        val typeName = parser.tokenAndAdvance.value as String
        val typeArgs = arrayListOf<TypeArgument>()

        instanceBody {
            typeArgs.add(evalTypeArgument())
            parser.skipNewLines()
            while (TokenType.COMMA == parser.token.kind) {
                parser.advanceCursor()
                parser.skipNewLines()
                typeArgs.add(evalTypeArgument())
                parser.skipNewLines()
            }
        }

        return TypeInstantiationNode(typeName, typeArgs.toList())
    }

    /**
     * Evaluates the instantiation body (if available).
     */
    private inline fun instanceBody(fn: () -> Unit) {
        if (TokenType.LEFT_CURLY_BRACE != parser.token.kind) return
        parser.advanceCursor()
        parser.skipNewLines()

        if (TokenType.RIGHT_CURLY_BRACE != parser.token.kind) {
            fn()
            parser.skipNewLines()
        }

        if (TokenType.RIGHT_CURLY_BRACE != parser.token.kind) parser.flagError(ErrorCode.MISSING_BLOCK_RIGHT_CURLY_BRACE)
        parser.advanceCursor()
    }

    /**
     * Evaluates the next tokens as type argument.
     */
    private fun evalTypeArgument(): TypeArgument {
        var name: String? = null
        if (TokenType.IDENTIFIER == parser.token.kind &&
                TokenType.ASSIGNMENT == parser.peekNextToken.kind) {
            name = parser.tokenAndAdvance.value as String
            parser.advanceCursor()
        }
        val valueExpr = ExprEval(parser).eval()
        return TypeArgument(name, valueExpr)
    }
}