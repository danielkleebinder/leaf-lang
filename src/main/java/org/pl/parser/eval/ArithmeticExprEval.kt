package org.pl.parser.eval

import org.pl.lexer.token.arithmetic.MinusToken
import org.pl.lexer.token.arithmetic.PlusToken
import org.pl.parser.IParser
import org.pl.parser.advance
import org.pl.parser.ast.BinaryOperation
import org.pl.parser.ast.BinaryOperationNode
import org.pl.parser.ast.INode

/**
 * Evaluates the arithmetic semantics:
 *
 * <arith-expr> ::= <term> (( PLUS | MINUS ) <term>)*
 *
 */
class ArithmeticExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val term = TermEval(parser)
        var node = term.eval()
        while (true) {
            node = when (parser.token::class) {
                PlusToken::class -> parser.advance { BinaryOperationNode(node, term.eval(), BinaryOperation.PLUS) }
                MinusToken::class -> parser.advance { BinaryOperationNode(node, term.eval(), BinaryOperation.MINUS) }
                else -> break
            }
        }
        return node
    }
}