package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.CommaToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.bracket.LeftBracketToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.bracket.RightParenthesisToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.advanceBeforeAfter
import org.nyxlang.parser.ast.*
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the call semantics:
 *
 * <call> ::= <atom>
 *          | <name>
 *          | <name> '[' <expr> ']'
 *          | <name> '(' (<expr> (',' <expr>)*)? ')'
 *
 */
class CallEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        if (NameToken::class == parser.token::class) {
            return when (parser.peekNextToken::class) {
                LeftParenthesisToken::class -> evalFunCall()
                LeftBracketToken::class -> evalGet()
                else -> VarAccessNode((parser.tokenAndAdvance as NameToken).value)
            }
        }
        return AtomEval(parser).eval()
    }

    /**
     * Evaluates the get access.
     */
    private fun evalGet(): BinaryOperationNode {
        val accessNode = VarAccessNode((parser.tokenAndAdvance as NameToken).value)
        return parser.advanceBeforeAfter {
            BinaryOperationNode(
                    accessNode,
                    ExprEval(parser).eval(),
                    BinaryOperation.GET)
        }
    }

    /**
     * Evaluates the function call.
     */
    private fun evalFunCall(): FunCallNode {
        val name = (parser.tokenAndAdvance as NameToken).value
        val funArgs = arrayListOf<INode>()
        funCallArgs {
            val expr = ExprEval(parser)
            funArgs.add(expr.eval())
            while (CommaToken::class == parser.token::class) {
                parser.advance { funArgs.add(expr.eval()) }
            }
        }
        return FunCallNode(name, funArgs)
    }

    /**
     * Checks if all requirements are fulfilled for a function call and invoked
     * the given [lambda].
     */
    private inline fun funCallArgs(lambda: () -> Unit) {
        if (LeftParenthesisToken::class != parser.token::class) throw EvalException("Opening parenthesis required for function call")
        parser.advanceCursor()

        // Is the argument list non empty?
        if (RightParenthesisToken::class != parser.token::class) {
            lambda()
        }

        if (RightParenthesisToken::class != parser.token::class) throw EvalException("Closing parenthesis required for function call")
        parser.advanceCursor()
    }
}