package org.nyxlang.parser.eval.expression

import org.nyxlang.lexer.token.ComplementToken
import org.nyxlang.lexer.token.arithmetic.MinusToken
import org.nyxlang.lexer.token.arithmetic.PlusToken
import org.nyxlang.lexer.token.logical.LogicalNotToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.UnaryOperation
import org.nyxlang.parser.ast.UnaryOperationNode
import org.nyxlang.parser.eval.IEval

/**
 * Evaluates the additive semantics:
 *
 * <prefix-expr> ::= ('!' | '+' | '-' | '~')? <postfix-expr>
 *
 */
class PrefixExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val postfixExpr = PostfixExprEval(parser)
        println(parser.token)
        return when (parser.token::class) {
            PlusToken::class -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.POSITIVE) }
            MinusToken::class -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.NEGATE) }
            LogicalNotToken::class -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.LOGICAL_NEGATE) }
            ComplementToken::class -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.BIT_COMPLEMENT) }
            else -> postfixExpr.eval()
        }
    }
}