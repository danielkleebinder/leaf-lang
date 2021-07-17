package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.logical.LogicalAndToken
import org.nyxlang.lexer.token.logical.LogicalOrToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

/**
 * Evaluates the expression semantics:
 *
 * <expr> ::= <logical-expr> (( AND | OR ) <logical-expr>)*
 *
 */
class ExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val equalExpr = EqualExprEval(parser)

        var node = equalExpr.eval()
        while (true) {
            node = when (parser.token::class) {
                LogicalAndToken::class -> parser.advance { BinaryOperationNode(node, equalExpr.eval(), BinaryOperation.LOGICAL_AND) }
                LogicalOrToken::class -> parser.advance { BinaryOperationNode(node, equalExpr.eval(), BinaryOperation.LOGICAL_OR) }
                else -> break
            }
        }
        return node
    }
}