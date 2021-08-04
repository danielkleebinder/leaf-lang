package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advanceAndSkipNewLines
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

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
            node = when (parser.token.kind) {
                TokenType.LESS -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, rangeExpr.eval(), BinaryOperation.LESS) }
                TokenType.LESS_EQUALS -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, rangeExpr.eval(), BinaryOperation.LESS_EQUALS) }
                TokenType.GREATER -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, rangeExpr.eval(), BinaryOperation.GREATER) }
                TokenType.GREATER_EQUALS -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, rangeExpr.eval(), BinaryOperation.GREATER_EQUALS) }
                else -> break
            }
        }
        return node
    }
}