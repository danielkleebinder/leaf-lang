package org.nyxlang.parser.eval.expression

import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.CommaToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.arithmetic.DecrementToken
import org.nyxlang.lexer.token.arithmetic.IncrementToken
import org.nyxlang.lexer.token.bracket.LeftBracketToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.bracket.RightBracketToken
import org.nyxlang.lexer.token.bracket.RightParenthesisToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.*
import org.nyxlang.parser.eval.AtomEval
import org.nyxlang.parser.eval.IEval
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the additive semantics:
 *
 * <postfix-expr> ::= <atom> (<suffix>)?
 *
 * <suffix> ::= ('++' | '--' | '?')
 *            | <assign-suffix>
 *            | <index-suffix>
 *            | <call-suffix>
 *
 * <assign-suffix> ::= '=' (NL)* <expr>
 * <index-suffix>  ::= '[' (NL)* <expr> (NL)* ']'
 * <call-suffix>   ::= '(' (NL)* (<expr> ((NL)* ',' (NL)* <expr>))? (NL)* ')'
 *
 */
class PostfixExprEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        var id: String? = null
        if (NameToken::class == parser.token::class) {
            id = (parser.token as NameToken).value
        }

        val atom = AtomEval(parser)
        val expr = ExprEval(parser)

        val node = atom.eval()
        return when (parser.token::class) {

            IncrementToken::class -> parser.advance { UnaryOperationNode(node, UnaryOperation.INCREMENT) }
            DecrementToken::class -> parser.advance { UnaryOperationNode(node, UnaryOperation.DECREMENT) }

            AssignToken::class -> {
                if (id == null) return node

                parser.advance {
                    parser.skipNewLines()
                    AssignmentNode(id, expr.eval())
                }
            }

            LeftBracketToken::class -> parser.advance {
                parser.skipNewLines()
                val result = expr.eval()
                parser.skipNewLines()
                if (RightBracketToken::class != parser.token::class) throw EvalException("Index access requires closing bracket")
                parser.advanceCursor()
                AccessNode(id!!, result)
            }

            LeftParenthesisToken::class -> {
                val funArgs = arrayListOf<INode>()
                funCallArgs {
                    funArgs.add(expr.eval())
                    while (CommaToken::class == parser.token::class) {
                        parser.advanceCursor()
                        parser.skipNewLines()
                        funArgs.add(expr.eval())
                    }
                }
                FunCallNode(id!!, funArgs)
            }
            else -> node
        }
    }

    /**
     * Checks if all requirements are fulfilled for a function call and invoked
     * the given [lambda].
     */
    private inline fun funCallArgs(lambda: () -> Unit) {
        if (LeftParenthesisToken::class != parser.token::class) throw EvalException("Opening parenthesis required for function call")
        parser.advanceCursor()
        parser.skipNewLines()

        // Is the argument list non empty?
        if (RightParenthesisToken::class != parser.token::class) {
            lambda()
        }

        if (RightParenthesisToken::class != parser.token::class) throw EvalException("Closing parenthesis required for function call")
        parser.advanceCursor()
    }
}