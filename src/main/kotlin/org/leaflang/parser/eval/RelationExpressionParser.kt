package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the relation semantics:
 *
 * <rel-expr> ::= <ran-expr> ((NL)* ( '<' | '<=' | '>' | '>=' ) (NL)* <ran-expr>)*
 *
 */
class RelationExpressionParser(private val parser: ILeafParser,
                               private val parserFactory: IParserFactory) : IParser {

    override fun parse(): INode {
        val rangeExpr = parserFactory.rangeExpressionParser
        var node = rangeExpr.parse()
        while (true) {
            node = when (parser.token.kind) {
                TokenType.LESS -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, rangeExpr.parse(), BinaryOperation.LESS) }
                TokenType.LESS_EQUALS -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, rangeExpr.parse(), BinaryOperation.LESS_EQUALS) }
                TokenType.GREATER -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, rangeExpr.parse(), BinaryOperation.GREATER) }
                TokenType.GREATER_EQUALS -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, rangeExpr.parse(), BinaryOperation.GREATER_EQUALS) }
                else -> break
            }
        }
        return node
    }
}