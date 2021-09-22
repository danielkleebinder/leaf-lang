package org.leaflang.parser.syntax

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.EmptyNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.value.ArrayNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the array semantics:
 *
 * <arr-expr> ::= '[' (NL)* (<expr> ((NL)* ',' (NL)* <expr>)*)? (NL)* ']'
 *
 */
class ArrayExpressionParser(private val parser: ILeafParser,
                            private val parserFactory: IParserFactory) : IParser {

    override fun parse(): ArrayNode {
        val expr = parserFactory.expressionParser
        val elements = arrayListOf<INode>()
        val pos = parser.nodePosition()

        enclosingBrackets {
            elements.add(expr.parse())
            parser.skipNewLines()

            while (TokenType.COMMA == parser.token.kind) {
                parser.advanceCursor()
                parser.skipNewLines()

                val exprValue = expr.parse()
                if (EmptyNode::class != exprValue::class) {
                    elements.add(exprValue)
                }
                parser.skipNewLines()
            }
        }

        return ArrayNode(pos, elements)
    }

    /**
     * Evaluates that the enclosing brackets are present and executes the given
     * [lambda] if the content is non-empty.
     */
    private inline fun enclosingBrackets(lambda: () -> Unit) {
        if (TokenType.LEFT_BRACKET != parser.token.kind) parser.flagError(ErrorCode.MISSING_LEFT_BRACKET)
        parser.advanceCursor()
        parser.skipNewLines()

        if (TokenType.RIGHT_BRACKET != parser.token.kind) {
            lambda()
            parser.skipNewLines()
        }

        if (TokenType.RIGHT_BRACKET != parser.token.kind) parser.flagError(ErrorCode.MISSING_RIGHT_BRACKET)
        parser.advanceCursor()
    }
}