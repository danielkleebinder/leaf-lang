package org.leaflang.parser.eval

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
        return when (parser.token.kind) {
            TokenType.PLUS -> parser.advance { UnaryOperationNode(parse(), UnaryOperation.POSITIVE) }
            TokenType.MINUS -> parser.advance { UnaryOperationNode(parse(), UnaryOperation.NEGATE) }
            TokenType.LOGICAL_NOT -> parser.advance { UnaryOperationNode(parse(), UnaryOperation.LOGICAL_NEGATE) }
            TokenType.COMPLEMENT -> parser.advance { UnaryOperationNode(parse(), UnaryOperation.BIT_COMPLEMENT) }
            else -> parserFactory.atomParser.parse()
        }
    }
}