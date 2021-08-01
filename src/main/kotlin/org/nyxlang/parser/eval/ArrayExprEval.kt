package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.CommaToken
import org.nyxlang.lexer.token.bracket.LeftBracketToken
import org.nyxlang.lexer.token.bracket.RightBracketToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.ArrayNode
import org.nyxlang.parser.ast.EmptyNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.exception.EvalException

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

            while (CommaToken::class == parser.token::class) {
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
        if (LeftBracketToken::class != parser.token::class) throw EvalException("Arrays require opening bracket")
        parser.advanceCursor()
        parser.skipNewLines()

        if (RightBracketToken::class != parser.token::class) {
            lambda()
            parser.skipNewLines()
        }

        if (RightBracketToken::class != parser.token::class) throw EvalException("Arrays require closing bracket")
        parser.advanceCursor()
    }
}