package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advanceAndSkipNewLines
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

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