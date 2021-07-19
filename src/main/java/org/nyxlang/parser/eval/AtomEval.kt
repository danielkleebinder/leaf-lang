package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.*
import org.nyxlang.lexer.token.arithmetic.DecrementToken
import org.nyxlang.lexer.token.arithmetic.IncrementToken
import org.nyxlang.lexer.token.arithmetic.MinusToken
import org.nyxlang.lexer.token.arithmetic.PlusToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.keyword.ConditionalKeywordToken
import org.nyxlang.lexer.token.keyword.LoopKeywordToken
import org.nyxlang.lexer.token.keyword.WhenKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.*

/**
 * Evaluates atoms with the following semantic:
 *
 * <atom> ::= ('+' | '-' | '~' | '++' | '--')? (<number> | <var>)
 *          | '(' <expr> ')'
 *          | <conditional-stmt>
 *          | <when-stmt>
 *          | <loop-stmt>
 *          | <native-stmt>
 *          | <empty>
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
            WhenKeywordToken::class -> return WhenEval(parser).eval()
            LoopKeywordToken::class -> return LoopEval(parser).eval()

            PlusToken::class -> parser.advance { return UnaryOperationNode(eval(), UnaryOperation.POSITIVE) }
            MinusToken::class -> parser.advance { return UnaryOperationNode(eval(), UnaryOperation.NEGATE) }
            IncrementToken::class -> parser.advance { return UnaryOperationNode(eval(), UnaryOperation.INCREMENT) }
            DecrementToken::class -> parser.advance { return UnaryOperationNode(eval(), UnaryOperation.DECREMENT) }
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
