package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.IParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode

/**
 * Evaluates the additive semantics:
 *
 * <add-expr> ::= <mul-expr> ((NL)* ( '+' | '-' ) (NL)* <mul-expr>)*
 *
 */
class AdditiveExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val multiplicativeExpr = MultiplicativeExprEval(parser)
        var node = multiplicativeExpr.eval()
        while (true) {
            node = when (parser.token.kind) {
                TokenType.PLUS -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, multiplicativeExpr.eval(), BinaryOperation.PLUS) }
                TokenType.MINUS -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, multiplicativeExpr.eval(), BinaryOperation.MINUS) }
                else -> break
            }
        }
        return node
    }
}