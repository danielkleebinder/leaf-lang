package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.IParserFactory
import org.leaflang.parser.utils.fromToken

/**
 * Evaluates the equality semantics:
 *
 * <equ-expr> ::= <rel-expr> ((NL)* ( '==' | '!=' ) (NL)* <rel-expr>)*
 *
 */
class EqualityExpressionParser(private val parser: ILeafParser,
                               private val parserFactory: IParserFactory) : IParser {

    override fun parse(): INode {
        val relExpr = parserFactory.relationExpressionParser
        val pos = parser.nodePosition()
        var node = relExpr.parse()
        while (true) {
            node = when (parser.token.kind) {
                TokenType.EQUALS -> parser.advanceAndSkipNewLines { BinaryOperationNode(pos, node, relExpr.parse(), BinaryOperation.EQUAL) }
                TokenType.NOT_EQUALS -> parser.advanceAndSkipNewLines { BinaryOperationNode(pos, node, relExpr.parse(), BinaryOperation.NOT_EQUAL) }
                else -> break
            }
        }
        return node
    }
}