package org.pl.parser.eval

import org.pl.lexer.token.logical.*
import org.pl.parser.IParser
import org.pl.parser.advance
import org.pl.parser.ast.*


/**
 * Evaluates the logic semantics:
 *
 * <logic-expr> ::= NOT <logical-expr>
 *                | <arith-expr> (( LT | LTE | GT | GTE ) <arith-expr>)*

 */
class LogicalExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        var node = if (LogicalNotToken::class == parser.token::class) {
            parser.advanceCursor()
            UnaryOperationNode(eval(), UnaryOperation.LOGICAL_NEGATE)
        } else {
            ArithmeticExprEval(parser).eval()
        }

        while (true) {
            node = when (parser.token::class) {
                EqualToken::class -> parser.advance {
                    BinaryOperationNode(node, ArithmeticExprEval(parser).eval(), BinaryOperation.EQUAL)
                }
                NotEqualToken::class -> parser.advance {
                    BinaryOperationNode(node, ArithmeticExprEval(parser).eval(), BinaryOperation.NOT_EQUAL)
                }
                LessThanToken::class -> parser.advance {
                    BinaryOperationNode(node, ArithmeticExprEval(parser).eval(), BinaryOperation.LESS_THAN)
                }
                LessThanOrEqualToken::class -> parser.advance {
                    BinaryOperationNode(node, ArithmeticExprEval(parser).eval(), BinaryOperation.LESS_THAN_OR_EQUAL)
                }
                GreaterThanToken::class -> parser.advance {
                    BinaryOperationNode(node, ArithmeticExprEval(parser).eval(), BinaryOperation.GREATER_THAN)
                }
                GreaterThanOrEqualToken::class -> parser.advance {
                    BinaryOperationNode(node, ArithmeticExprEval(parser).eval(), BinaryOperation.GREATER_THAN_OR_EQUAL)
                }
                else -> break
            }
        }

        return node
    }
}