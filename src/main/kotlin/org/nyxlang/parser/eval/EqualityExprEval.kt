package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advanceAndSkipNewLines
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

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