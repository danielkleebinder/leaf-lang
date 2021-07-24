package org.nyxlang.parser.eval.expression

import org.nyxlang.lexer.token.logical.GreaterThanOrEqualToken
import org.nyxlang.lexer.token.logical.GreaterThanToken
import org.nyxlang.lexer.token.logical.LessThanOrEqualToken
import org.nyxlang.lexer.token.logical.LessThanToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.eval.IEval

/**
 * Evaluates the relation semantics:
 *
 * <rel-expr> ::= <ran-expr> ((NL)* ( '<' | '<=' | '>' | '>=' ) (NL)* <ran-expr>)*
 *
 */
class RelationExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val rangeExpr = RangeExprEval(parser)

        var node = rangeExpr.eval()
        while (true) {
            parser.skipNewLines()
            node = when (parser.token::class) {
                LessThanToken::class -> parser.advance { BinaryOperationNode(node, rangeExpr.eval(), BinaryOperation.LESS_THAN) }
                LessThanOrEqualToken::class -> parser.advance { BinaryOperationNode(node, rangeExpr.eval(), BinaryOperation.LESS_THAN_OR_EQUAL) }
                GreaterThanToken::class -> parser.advance { BinaryOperationNode(node, rangeExpr.eval(), BinaryOperation.GREATER_THAN) }
                GreaterThanOrEqualToken::class -> parser.advance { BinaryOperationNode(node, rangeExpr.eval(), BinaryOperation.GREATER_THAN_OR_EQUAL) }
                else -> break
            }
        }
        return node
    }
}