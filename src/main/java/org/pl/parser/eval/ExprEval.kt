package org.pl.parser.eval

import org.pl.lexer.token.logical.LogicalAndToken
import org.pl.lexer.token.logical.LogicalOrToken
import org.pl.parser.IParser
import org.pl.parser.advance
import org.pl.parser.ast.BinaryOperation
import org.pl.parser.ast.BinaryOperationNode
import org.pl.parser.ast.INode

/**
 * Evaluates the expression semantics:
 *
 * <expr> ::= <logical-expr> (( AND | OR ) <logical-expr>)*
 *
 */
class ExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val logicalExpr = LogicalExprEval(parser)

        var node = logicalExpr.eval()
        while (true) {
            node = when (parser.token::class) {
                LogicalAndToken::class -> parser.advance { BinaryOperationNode(node, logicalExpr.eval(), BinaryOperation.LOGICAL_AND) }
                LogicalOrToken::class -> parser.advance { BinaryOperationNode(node, logicalExpr.eval(), BinaryOperation.LOGICAL_OR) }
                else -> break
            }
        }
        return node
    }
}