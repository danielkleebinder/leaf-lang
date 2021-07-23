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
import org.nyxlang.parser.advanceBeforeAfter
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
        return when (parser.token::class) {

            NumberToken::class -> NumberNode((parser.tokenAndAdvance as NumberToken).value)
            BoolToken::class -> BoolNode((parser.tokenAndAdvance as BoolToken).value)
            StringToken::class -> StringNode((parser.tokenAndAdvance as StringToken).value)
            NameToken::class -> {
                if (LeftParenthesisToken::class == parser.peekNextToken::class) {
                    FunCallEval(parser).eval()
                } else {
                    VarAccessNode((parser.tokenAndAdvance as NameToken).value)
                }
            }

            ConditionalKeywordToken::class -> ConditionalEval(parser).eval()
            WhenKeywordToken::class -> WhenEval(parser).eval()
            LoopKeywordToken::class -> LoopEval(parser).eval()

            PlusToken::class -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.POSITIVE) }
            MinusToken::class -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.NEGATE) }
            IncrementToken::class -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.INCREMENT) }
            DecrementToken::class -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.DECREMENT) }
            ComplementToken::class -> parser.advance { UnaryOperationNode(eval(), UnaryOperation.BIT_COMPLEMENT) }

            LeftParenthesisToken::class -> parser.advanceBeforeAfter { ExprEval(parser).eval() }

            else -> EmptyNode()
        }
    }
}
