package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.arithmetic.DivideToken
import org.nyxlang.lexer.token.arithmetic.ModToken
import org.nyxlang.lexer.token.arithmetic.MultiplyToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

/**
 * Evaluates the term semantics:
 *
 * <term> ::= <call> (( '*' | '/' | '%' ) <call>)*
 *
 */
class TermEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val call = CallEval(parser)
        var node = call.eval()
        while (true) {
            node = when (parser.token::class) {
                DivideToken::class -> parser.advance { BinaryOperationNode(node, call.eval(), BinaryOperation.DIV) }
                ModToken::class -> parser.advance { BinaryOperationNode(node, call.eval(), BinaryOperation.REM) }
                MultiplyToken::class -> parser.advance { BinaryOperationNode(node, call.eval(), BinaryOperation.TIMES) }
                else -> break
            }
        }
        return node
    }
}