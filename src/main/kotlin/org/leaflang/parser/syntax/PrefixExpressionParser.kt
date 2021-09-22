package org.leaflang.parser.syntax

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advance
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.UnaryOperation
import org.leaflang.parser.ast.UnaryOperationNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the additive semantics:
 *
 * <pre-expr> ::= ('!' | '+' | '-' | '~')? <atom>
 *
 */
class PrefixExpressionParser(private val parser: ILeafParser,
                             private val parserFactory: IParserFactory) : IParser {

    override fun parse(): INode {
        val pos = parser.nodePosition()
        return when (parser.token.kind) {
            TokenType.PLUS -> parser.advance { UnaryOperationNode(pos, parse(), UnaryOperation.POSITIVE) }
            TokenType.MINUS -> parser.advance { UnaryOperationNode(pos, parse(), UnaryOperation.NEGATE) }
            TokenType.LOGICAL_NOT -> parser.advance { UnaryOperationNode(pos, parse(), UnaryOperation.LOGICAL_NEGATE) }
            TokenType.COMPLEMENT -> parser.advance { UnaryOperationNode(pos, parse(), UnaryOperation.BIT_COMPLEMENT) }
            else -> parserFactory.atomParser.parse()
        }
    }
}