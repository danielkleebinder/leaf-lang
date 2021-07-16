package org.pl.parser.eval

import org.pl.lexer.token.logical.EqualToken
import org.pl.lexer.token.logical.NotEqualToken
import org.pl.parser.IParser
import org.pl.parser.advance
import org.pl.parser.ast.BinaryOperation
import org.pl.parser.ast.BinaryOperationNode
import org.pl.parser.ast.INode

/**
 * Evaluates the equality semantics:
 *
 * <equal-expr> ::= <logical-expr> (( EQ | NEQ ) <logical-expr>)*
 *
 */
class EqualExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val logicalExpr = LogicalExprEval(parser)

        var node = logicalExpr.eval()
        while (true) {
            node = when (parser.token::class) {
                EqualToken::class -> parser.advance { BinaryOperationNode(node, logicalExpr.eval(), BinaryOperation.EQUAL) }
                NotEqualToken::class -> parser.advance { BinaryOperationNode(node, logicalExpr.eval(), BinaryOperation.NOT_EQUAL) }
                else -> break
            }
        }
        return node
    }
}