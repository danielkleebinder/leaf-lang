package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the additive semantics:
 *
 * <add-expr> ::= <mul-expr> ((NL)* ( '+' | '-' ) (NL)* <mul-expr>)*
 *
 */
class AdditiveExpressionParser(private val parser: ILeafParser,
                               private val parserFactory: IParserFactory) : IParser {

    override fun parse(): INode {
        val multExpr = parserFactory.multiplicativeExpressionParser
        var node = multExpr.parse()
        while (true) {
            val pos = parser.nodePosition()
            node = when (parser.token.kind) {
                TokenType.PLUS -> parser.advanceAndSkipNewLines { BinaryOperationNode(pos, node, multExpr.parse(), BinaryOperation.PLUS) }
                TokenType.MINUS -> parser.advanceAndSkipNewLines { BinaryOperationNode(pos, node, multExpr.parse(), BinaryOperation.MINUS) }
                else -> break
            }
        }
        return node
    }
}