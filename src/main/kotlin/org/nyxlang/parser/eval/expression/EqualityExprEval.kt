package org.nyxlang.parser.eval.expression

import org.nyxlang.lexer.token.logical.EqualToken
import org.nyxlang.lexer.token.logical.NotEqualToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.eval.IEval

/**
 * Evaluates the equality semantics:
 *
 * <equ-expr> ::= <rel-expr> ((NL)* ( '==' | '!=' ) (NL)* <rel-expr>)*
 *
 */
class EqualityExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val relationExpr = RelationExprEval(parser)

        var node = relationExpr.eval()
        while (true) {
            parser.skipNewLines()
            node = when (parser.token::class) {
                EqualToken::class -> parser.advance { BinaryOperationNode(node, relationExpr.eval(), BinaryOperation.EQUAL) }
                NotEqualToken::class -> parser.advance { BinaryOperationNode(node, relationExpr.eval(), BinaryOperation.NOT_EQUAL) }
                else -> break
            }
        }
        return node
    }
}