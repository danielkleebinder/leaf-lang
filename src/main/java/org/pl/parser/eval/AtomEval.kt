package org.pl.parser.eval

import org.pl.lexer.token.*
import org.pl.lexer.token.arithmetic.MinusToken
import org.pl.lexer.token.arithmetic.PlusToken
import org.pl.lexer.token.bracket.LeftParenthesisToken
import org.pl.lexer.token.keyword.ConditionalKeywordToken
import org.pl.lexer.token.keyword.LoopKeywordToken
import org.pl.parser.IParser
import org.pl.parser.advance
import org.pl.parser.ast.*
import org.pl.parser.exception.EvalException

/**
 * Evaluates atoms with the following semantic:
 *
 * <atom> ::= PLUS <number>
 *          | MINUS <number>
 *          | COMPLEMENT <number>
 *          | LPAREN <expr> RPAREN
 *          | <var>
 *          | <conditional-expr>
 *          | <loop-expr>
 *
 */
class AtomEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        when (parser.token::class) {

            NumberToken::class -> return NumberNode((parser.tokenAndAdvance as NumberToken).getValue())
            BoolToken::class -> return BoolNode((parser.tokenAndAdvance as BoolToken).getValue())
            NameToken::class -> return VarAccessNode((parser.tokenAndAdvance as NameToken).getValue())
            EndOfProgramToken::class -> return EmptyNode()

            ConditionalKeywordToken::class -> return ConditionalEval(parser).eval()
            LoopKeywordToken::class -> return LoopEval(parser).eval()


            PlusToken::class -> parser.advance { return UnaryOperationNode(eval(), UnaryOperation.POSITIVE) }
            MinusToken::class -> parser.advance { return UnaryOperationNode(eval(), UnaryOperation.NEGATE) }
            ComplementToken::class -> parser.advance { return UnaryOperationNode(eval(), UnaryOperation.BIT_COMPLEMENT) }

            LeftParenthesisToken::class -> {
                parser.advanceCursor()
                val result = ExprEval(parser).eval()
                parser.advanceCursor()
                return result
            }
        }
        return EmptyNode()
    }
}
