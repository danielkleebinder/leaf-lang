package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.IParser
import org.leaflang.parser.advance
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.UnaryOperation
import org.leaflang.parser.ast.UnaryOperationNode

/**
 * Evaluates the additive semantics:
 *
 * <pre-expr> ::= ('!' | '+' | '-' | '~')? <atom>
 *
 */
class PrefixExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val postfixExpr = AtomEval(parser)
        return when (parser.token.kind) {
            TokenType.PLUS -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.POSITIVE) }
            TokenType.MINUS -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.NEGATE) }
            TokenType.LOGICAL_NOT -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.LOGICAL_NEGATE) }
            TokenType.COMPLEMENT -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.BIT_COMPLEMENT) }
            else -> postfixExpr.eval()
        }
    }
}