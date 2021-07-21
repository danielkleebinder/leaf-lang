package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.arithmetic.MinusToken
import org.nyxlang.lexer.token.arithmetic.PlusToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

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