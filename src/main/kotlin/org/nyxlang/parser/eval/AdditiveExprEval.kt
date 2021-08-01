package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.arithmetic.MinusToken
import org.nyxlang.lexer.token.arithmetic.PlusToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

/**
 * Evaluates the additive semantics:
 *
 * <add-expr> ::= <mul-expr> ((NL)* ( '+' | '-' ) (NL)* <mul-expr>)*
 *
 */
class AdditiveExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val multiplicativeExpr = MultiplicativeExprEval(parser)
        var node = multiplicativeExpr.eval()
        while (true) {

            node = when (parser.token::class) {
                PlusToken::class -> parser.advance {
                    parser.skipNewLines()
                    BinaryOperationNode(node, multiplicativeExpr.eval(), BinaryOperation.PLUS)
                }
                MinusToken::class -> parser.advance {
                    parser.skipNewLines()
                    BinaryOperationNode(node, multiplicativeExpr.eval(), BinaryOperation.MINUS)
                }
                else -> break
            }
        }
        return node
    }
}