package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.INode

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
            node = when (parser.token.kind) {
                TokenType.RANGE -> TODO("Range is not supported yet")
                else -> break
            }
        }
        return node
    }
}