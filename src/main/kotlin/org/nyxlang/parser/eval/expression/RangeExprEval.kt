package org.nyxlang.parser.eval.expression

import org.nyxlang.lexer.token.RangeToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.eval.IEval

/**
 * Evaluates the range semantics:
 *
 * <ran-expr> ::= <add-expr> ((NL)* ( '..' ) (NL)* <add-expr>)*
 *
 */
class RangeExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val additiveExpr = AdditiveExprEval(parser)

        var node = additiveExpr.eval()
        while (true) {
            node = when (parser.token::class) {
                RangeToken::class -> TODO("Range is not supported yet")
                else -> break
            }
        }
        return node
    }
}