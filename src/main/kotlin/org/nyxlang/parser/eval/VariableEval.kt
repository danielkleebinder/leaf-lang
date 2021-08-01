package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.CommaToken
import org.nyxlang.lexer.token.DotToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.bracket.LeftBracketToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.bracket.RightBracketToken
import org.nyxlang.lexer.token.bracket.RightParenthesisToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.*
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the additive semantics:
 *
 * <var> ::= <name>
 *         | <name> '.' <name>
 *         | <name> '[' (NL)* <expr> (NL*) ']'
 *         | <name> '(' (NL)* (<expr> ((NL)* ',' (NL)* <expr>))? (NL)* ')'
 *
 */
class VariableEval(private val parser: IParser) : IEval {

    /**
     * Identifiers that are used for accessing children.
     */
    private val childIdentifier = arrayOf(
            DotToken::class,
            LeftBracketToken::class,
            LeftParenthesisToken::class)


    override fun eval(): AccessNode {
        if (NameToken::class != parser.token::class) throw EvalException("Name identifier expected, but got ${parser.token}")

        val id = (parser.tokenAndAdvance as NameToken).value
        val children = arrayListOf<INode>()

        while (childIdentifier.contains(parser.token::class)) {
            when (parser.token::class) {
                DotToken::class -> children.add(evalFieldAccess())
                LeftBracketToken::class -> children.add(evalIndexAccess())
                LeftParenthesisToken::class -> children.add(evalCallAccess())
            }
        }

        return AccessNode(id, children.toList())
    }

    /**
     * Evaluates member field access (e.g. 'foo.bar') or throws an exception if syntactic errors occurred.
     */
    private fun evalFieldAccess(): AccessFieldNode {
        if (DotToken::class != parser.token::class) throw EvalException("Dot required for member field access")
        parser.advanceCursor()
        parser.skipNewLines()

        if (NameToken::class != parser.token::class) throw EvalException("Name identifier expected after '.' during member field access")
        val name = (parser.tokenAndAdvance as NameToken).value
        return AccessFieldNode(name)
    }

    /**
     * Evaluates index based access (e.g. 'foo[10]') or throws an exception if syntactic errors occurred.
     */
    private fun evalIndexAccess(): AccessIndexNode {
        if (LeftBracketToken::class != parser.token::class) throw EvalException("Left bracket '[' required for index access")
        parser.advanceCursor()
        parser.skipNewLines()

        val indexExpr = ExprEval(parser).eval()

        if (RightBracketToken::class != parser.token::class) throw EvalException("Right bracket ']' required for index access")
        parser.advanceCursor()

        return AccessIndexNode(indexExpr)
    }

    /**
     * Evaluates a call (e.g. 'foo()') or throws an exception if syntactic errors occurred.
     */
    private fun evalCallAccess(): AccessCallNode {
        if (LeftParenthesisToken::class != parser.token::class) throw EvalException("Opening parenthesis required for function call")
        parser.advanceCursor()
        parser.skipNewLines()

        val args = arrayListOf<INode>()
        val expr = ExprEval(parser)

        // Is the argument list non empty?
        if (RightParenthesisToken::class != parser.token::class) {
            parser.skipNewLines()
            args.add(expr.eval())
            while (CommaToken::class == parser.token::class) {
                parser.advanceCursor()
                parser.skipNewLines()
                args.add(expr.eval())
            }
        }

        if (RightParenthesisToken::class != parser.token::class) throw EvalException("Closing parenthesis required for function call")
        parser.advanceCursor()

        return AccessCallNode(args.toList())
    }
}