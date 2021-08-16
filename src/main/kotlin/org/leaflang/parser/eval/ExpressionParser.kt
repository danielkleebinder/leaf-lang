package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the expression semantics:
 *
 * <expr> ::= <rel-expr> ((NL)* ( '&&' | '||' ) (NL)* <rel-expr>)*
 *
 */
class ExpressionParser(private val parser: ILeafParser,
                       private val parserFactory: IParserFactory) : IParser {

    override fun parse(): INode {
        val eqExpr = parserFactory.equalityExpressionParser
        var node = eqExpr.parse()
        while (true) {
            node = when (parser.token.kind) {
                TokenType.LOGICAL_AND -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, eqExpr.parse(), BinaryOperation.LOGICAL_AND) }
                TokenType.LOGICAL_OR -> parser.advanceAndSkipNewLines { BinaryOperationNode(node, eqExpr.parse(), BinaryOperation.LOGICAL_OR) }
                else -> break
            }
        }
        return node
    }
}