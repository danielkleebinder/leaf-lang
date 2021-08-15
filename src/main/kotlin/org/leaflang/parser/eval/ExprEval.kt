package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.IParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode

/**
 * Evaluates the expression semantics:
 *
 * <expr> ::= <rel-expr> ((NL)* ( '&&' | '||' ) (NL)* <rel-expr>)*
 *
 */
class ExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val equalityExpr = EqualityExprEval(parser)

        var node = equalityExpr.eval()
        while (true) {
            node = when (parser.token.kind) {
                TokenType.LOGICAL_AND -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, equalityExpr.eval(), BinaryOperation.LOGICAL_AND) }
                TokenType.LOGICAL_OR -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, equalityExpr.eval(), BinaryOperation.LOGICAL_OR) }
                else -> break
            }
        }
        return node
    }
}