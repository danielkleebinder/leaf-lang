package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.logical.EqualToken
import org.nyxlang.lexer.token.logical.NotEqualToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

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