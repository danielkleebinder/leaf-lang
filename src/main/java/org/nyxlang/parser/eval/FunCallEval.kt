package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.CommaToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.bracket.RightParenthesisToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.FunCallNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the function call semantics:
 *
 * <fun-call> ::= <name> '(' (<expr> (',' <expr>)*)? ')'
 *
 */
class FunCallEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        if (NameToken::class != parser.token::class) {
            throw EvalException("Name required for function call")
        }
        val funName = (parser.tokenAndAdvance as NameToken).getValue()
        val funArgs = arrayListOf<INode>()

        funCallArgs {
            val expr = ExprEval(parser)
            funArgs.add(expr.eval())
            while (CommaToken::class == parser.token::class) {
                parser.advanceCursor()
                funArgs.add(expr.eval())
            }
        }

        return FunCallNode(funName, funArgs)
    }

    private inline fun funCallArgs(body: () -> Unit) {
        if (LeftParenthesisToken::class != parser.token::class) {
            throw EvalException("Opening parenthesis required for function call")
        }
        parser.advanceCursor()

        // Is the argument list non empty?
        if (RightParenthesisToken::class != parser.token::class) {
            body()
        }

        if (RightParenthesisToken::class != parser.token::class) {
            throw EvalException("Closing parenthesis required for function call")
        }
        parser.advanceCursor()
    }
}