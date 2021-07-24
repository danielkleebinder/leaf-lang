package org.nyxlang.parser.eval.expression

import org.nyxlang.lexer.token.arithmetic.DivideToken
import org.nyxlang.lexer.token.arithmetic.ModToken
import org.nyxlang.lexer.token.arithmetic.MultiplyToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.eval.IEval

/**
 * Evaluates the multiplicative semantics:
 *
 * <mul-expr> ::= <prefix-expr> ((NL)* ( '*' | '/' | '%' ) (NL)* <prefix-expr>)*
 *
 */
class MultiplicativeExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val prefixExpr = PrefixExprEval(parser)
        var node = prefixExpr.eval()
        while (true) {
            parser.skipNewLines()
            node = when (parser.token::class) {
                MultiplyToken::class -> parser.advance { BinaryOperationNode(node, prefixExpr.eval(), BinaryOperation.TIMES) }
                DivideToken::class -> parser.advance { BinaryOperationNode(node, prefixExpr.eval(), BinaryOperation.DIV) }
                ModToken::class -> parser.advance { BinaryOperationNode(node, prefixExpr.eval(), BinaryOperation.REM) }
                else -> break
            }
        }
        return node
    }
}