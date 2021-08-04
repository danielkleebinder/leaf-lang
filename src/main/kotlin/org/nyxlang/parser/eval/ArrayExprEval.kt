package org.nyxlang.parser.eval

import org.nyxlang.error.ErrorCode
import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.ArrayNode
import org.nyxlang.parser.ast.EmptyNode
import org.nyxlang.parser.ast.INode

/**
 * Evaluates the array semantics:
 *
 * <arr-expr> ::= '[' (NL)* (<expr> ((NL)* ',' (NL)* <expr>)*)? (NL)* ']'
 *
 */
class ArrayExprEval(private val parser: IParser) : IEval {

    override fun eval(): ArrayNode {
        val expr = ExprEval(parser)
        val elements = arrayListOf<INode>()

        enclosingBrackets {
            elements.add(expr.eval())
            parser.skipNewLines()

            while (TokenType.COMMA == parser.token.kind) {
                parser.advanceCursor()
                parser.skipNewLines()

                val exprValue = expr.eval()
                if (EmptyNode::class != exprValue::class) {
                    elements.add(exprValue)
                }
                parser.skipNewLines()
            }
        }

        return ArrayNode(elements)
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