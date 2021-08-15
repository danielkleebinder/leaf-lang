package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.IParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode

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
            node = when (parser.token.kind) {
                TokenType.EQUALS -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, relationExpr.eval(), BinaryOperation.EQUAL) }
                TokenType.NOT_EQUALS -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, relationExpr.eval(), BinaryOperation.NOT_EQUAL) }
                else -> break
            }
        }
        return node
    }
}