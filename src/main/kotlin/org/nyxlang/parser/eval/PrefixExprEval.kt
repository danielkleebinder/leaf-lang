package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.UnaryOperation
import org.nyxlang.parser.ast.UnaryOperationNode

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