package org.nyxlang.parser.eval.expression

import org.nyxlang.lexer.token.arithmetic.MinusToken
import org.nyxlang.lexer.token.arithmetic.PlusToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.eval.IEval

/**
 * Evaluates the additive semantics:
 *
 * <add-expr> ::= <mul-expr> ((NL)* ( '+' | '-' ) (NL)* <mul-expr>)*
 *
 */
class AdditiveExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val multExpr = MultiplicativeExprEval(parser)
        var node = multExpr.eval()
        while (true) {
            parser.skipNewLines()
            node = when (parser.token::class) {
                PlusToken::class -> parser.advance { BinaryOperationNode(node, multExpr.eval(), BinaryOperation.PLUS) }
                MinusToken::class -> parser.advance { BinaryOperationNode(node, multExpr.eval(), BinaryOperation.MINUS) }
                else -> break
            }
        }
        return node
    }
}