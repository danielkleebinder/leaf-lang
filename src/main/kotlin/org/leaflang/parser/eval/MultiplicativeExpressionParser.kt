package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the multiplicative semantics:
 *
 * <mul-expr> ::= <prefix-expr> ((NL)* ( '*' | '/' | '%' ) (NL)* <prefix-expr>)*
 *
 */
class MultiplicativeExpressionParser(private val parser: ILeafParser,
                                     private val parserFactory: IParserFactory) : IParser {

    override fun parse(): INode {
        val prefixExpr = parserFactory.prefixExpressionParser
        val pos = parser.nodePosition()
        var node = prefixExpr.parse()
        while (true) {
            node = when (parser.token.kind) {
                TokenType.TIMES -> parser.advanceAndSkipNewLines { BinaryOperationNode(pos, node, prefixExpr.parse(), BinaryOperation.TIMES) }
                TokenType.DIV -> parser.advanceAndSkipNewLines { BinaryOperationNode(pos, node, prefixExpr.parse(), BinaryOperation.DIV) }
                TokenType.REM -> parser.advanceAndSkipNewLines { BinaryOperationNode(pos, node, prefixExpr.parse(), BinaryOperation.REM) }
                else -> break
            }
        }
        return node
    }
}