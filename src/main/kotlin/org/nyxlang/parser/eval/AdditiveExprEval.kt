package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advanceAndSkipNewLines
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

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