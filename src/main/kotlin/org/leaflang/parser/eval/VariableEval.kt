package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.IParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.*

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
            TokenType.DOT,
            TokenType.LEFT_BRACKET,
            TokenType.LEFT_PARENTHESIS)


    override fun eval(): AccessNode {
        if (TokenType.IDENTIFIER != parser.token.kind) parser.flagError(ErrorCode.MISSING_IDENTIFIER)

        val id = parser.tokenAndAdvance.value as String
        val children = arrayListOf<INode>()

        while (childIdentifier.contains(parser.token.kind)) {
            when (parser.token.kind) {
                TokenType.DOT -> parser.advanceAndSkipNewLines { children.add(evalFieldAccess()) }
                TokenType.LEFT_BRACKET -> parser.advanceAndSkipNewLines { children.add(evalIndexAccess()) }
                TokenType.LEFT_PARENTHESIS -> parser.advanceAndSkipNewLines { children.add(evalCallAccess()) }
            }
        }

        return AccessNode(id, children.toList())
    }

    /**
     * Evaluates member field access (e.g. 'foo.bar') or throws an exception if syntactic errors occurred.
     */
    private fun evalFieldAccess(): AccessFieldNode {
        if (TokenType.IDENTIFIER != parser.token.kind) parser.flagError(ErrorCode.MISSING_IDENTIFIER)
        val name = parser.tokenAndAdvance.value as String
        return AccessFieldNode(name)
    }

    /**
     * Evaluates index based access (e.g. 'foo[10]') or throws an exception if syntactic errors occurred.
     */
    private fun evalIndexAccess(): AccessIndexNode {
        val indexExpr = ExprEval(parser).eval()

        if (TokenType.RIGHT_BRACKET != parser.token.kind) parser.flagError(ErrorCode.MISSING_RIGHT_BRACKET)
        parser.advanceCursor()

        return AccessIndexNode(indexExpr)
    }

    /**
     * Evaluates a call (e.g. 'foo()') or throws an exception if syntactic errors occurred.
     */
    private fun evalCallAccess(): AccessCallNode {
        val args = arrayListOf<INode>()
        val expr = ExprEval(parser)

        // Is the argument list non empty?
        if (TokenType.RIGHT_PARENTHESIS != parser.token.kind) {
            parser.skipNewLines()
            args.add(expr.eval())
            while (TokenType.COMMA == parser.token.kind) {
                parser.advanceCursor()
                parser.skipNewLines()
                args.add(expr.eval())
            }
        }

        if (TokenType.RIGHT_PARENTHESIS != parser.token.kind) parser.flagError(ErrorCode.MISSING_RIGHT_PARENTHESIS)
        parser.advanceCursor()

        return AccessCallNode(args.toList())
    }
}