package org.pl.parser.eval

import org.pl.lexer.token.arithmetic.MinusToken
import org.pl.lexer.token.arithmetic.PlusToken
import org.pl.parser.IParser
import org.pl.parser.ast.BinaryOperation
import org.pl.parser.ast.BinaryOperationNode
import org.pl.parser.ast.INode

/**
 * Evaluates the equality semantics:
 *
 * <equal-expr> ::= <logical-expr> (( EQ | NEQ ) <logical-expr>)*
 *
 */
class EqualExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val term = TermEval(parser)

        var node = term.eval()
        while (true) {
            when (parser.token::class) {
                PlusToken::class -> {
                    parser.advanceCursor()
                    node = BinaryOperationNode(node, term.eval(), BinaryOperation.PLUS)
                }
                MinusToken::class -> {
                    parser.advanceCursor()
                    node = BinaryOperationNode(node, term.eval(), BinaryOperation.MINUS)
                }
                else -> break
            }
        }
        return node
    }
}