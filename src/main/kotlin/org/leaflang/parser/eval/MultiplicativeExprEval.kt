package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.IParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode

/**
 * Evaluates the multiplicative semantics:
 *
 * <mul-expr> ::= <prefix-expr> ((NL)* ( '*' | '/' | '%' ) (NL)* <prefix-expr>)*
 *
 */
class MultiplicativeExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val prefixExpr = PrefixExprEval(parser)
        var node = prefixExpr.eval()
        while (true) {
            node = when (parser.token.kind) {
                TokenType.TIMES -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, prefixExpr.eval(), BinaryOperation.TIMES) }
                TokenType.DIV -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, prefixExpr.eval(), BinaryOperation.DIV) }
                TokenType.REM -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, prefixExpr.eval(), BinaryOperation.REM) }
                else -> break
            }
        }
        return node
    }
}