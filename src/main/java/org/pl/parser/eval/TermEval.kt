package org.pl.parser.eval

import org.pl.lexer.token.arithmetic.DivideToken
import org.pl.lexer.token.arithmetic.ModToken
import org.pl.lexer.token.arithmetic.MultiplyToken
import org.pl.parser.IParser
import org.pl.parser.advance
import org.pl.parser.ast.BinaryOperation
import org.pl.parser.ast.BinaryOperationNode
import org.pl.parser.ast.INode

/**
 * Evaluates the term semantics:
 *
 * <term> ::= <atom> (( MULT | DIVIDE | MOD ) <atom>)*
 *
 */
class TermEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        val atom = AtomEval(parser)
        var node = atom.eval()
        while (true) {
            node = when (parser.token::class) {
                DivideToken::class -> parser.advance { BinaryOperationNode(node, atom.eval(), BinaryOperation.DIVIDE) }
                ModToken::class -> parser.advance { BinaryOperationNode(node, atom.eval(), BinaryOperation.MOD) }
                MultiplyToken::class -> parser.advance {
                    if (MultiplyToken::class == parser.token::class) {
                        parser.advanceCursor()
                        BinaryOperationNode(node, atom.eval(), BinaryOperation.POWER)
                    } else {
                        BinaryOperationNode(node, atom.eval(), BinaryOperation.MULTIPLY)
                    }
                }
                else -> break
            }
        }
        return node
    }
}