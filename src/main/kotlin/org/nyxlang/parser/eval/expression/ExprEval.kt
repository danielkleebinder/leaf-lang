package org.nyxlang.parser.eval.expression

import org.nyxlang.lexer.token.logical.LogicalAndToken
import org.nyxlang.lexer.token.logical.LogicalOrToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.eval.IEval

/**
 * Evaluates the expression semantics:
 *
 * <expr> ::= <rel-expr> ((NL)* ( '&&' | '||' ) (NL)* <rel-expr>)*
 *
 */
class ExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val equalityExpr = EqualityExprEval(parser)

        var node = equalityExpr.eval()
        while (true) {
            node = when (parser.token::class) {
                LogicalAndToken::class -> parser.advance {
                    parser.skipNewLines()
                    BinaryOperationNode(node, equalityExpr.eval(), BinaryOperation.LOGICAL_AND)
                }
                LogicalOrToken::class -> parser.advance {
                    parser.skipNewLines()
                    BinaryOperationNode(node, equalityExpr.eval(), BinaryOperation.LOGICAL_OR)
                }
                else -> break
            }
        }
        return node
    }
}