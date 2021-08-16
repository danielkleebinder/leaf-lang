package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.*
import org.leaflang.parser.ast.access.AccessCallNode
import org.leaflang.parser.ast.access.AccessFieldNode
import org.leaflang.parser.ast.access.AccessIndexNode
import org.leaflang.parser.ast.access.AccessNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the additive semantics:
 *
 * <var> ::= <name>
 *         | <name> '.' <name>
 *         | <name> '[' (NL)* <expr> (NL*) ']'
 *         | <name> '(' (NL)* (<expr> ((NL)* ',' (NL)* <expr>))? (NL)* ')'
 *
 */
class VariableParser(private val parser: ILeafParser,
                     private val parserFactory: IParserFactory) : IParser {

    /**
     * Identifiers that are used for accessing children.
     */
    private val childIdentifier = arrayOf(
            TokenType.DOT,
            TokenType.LEFT_BRACKET,
            TokenType.LEFT_PARENTHESIS)


    override fun parse(): AccessNode {
        if (TokenType.IDENTIFIER != parser.token.kind) parser.flagError(ErrorCode.MISSING_IDENTIFIER)

        val id = parser.tokenAndAdvance.value as String
        val children = arrayListOf<INode>()

        while (childIdentifier.contains(parser.token.kind)) {
            when (parser.token.kind) {
                TokenType.DOT -> parser.advanceAndSkipNewLines { children.add(evalFieldAccess()) }
                TokenType.LEFT_BRACKET -> parser.advanceAndSkipNewLines { children.add(evalIndexAccess()) }
                TokenType.LEFT_PARENTHESIS -> parser.advanceAndSkipNewLines { children.add(evalCallAccess()) }
                else -> parser.flagError(ErrorCode.INVALID_ACCESS)
            }
        }

        return AccessNode(id, children.toList())
    }

    /**
     * Evaluates member field access (e.g. 'foo.bar') and flag errors.
     */
    private fun evalFieldAccess(): AccessFieldNode {
        if (TokenType.IDENTIFIER != parser.token.kind) parser.flagError(ErrorCode.MISSING_IDENTIFIER)
        val name = parser.tokenAndAdvance.value as String
        return AccessFieldNode(name)
    }

    /**
     * Evaluates index based access (e.g. 'foo[10]') and flag errors.
     */
    private fun evalIndexAccess(): AccessIndexNode {
        val expr = parserFactory.expressionParser
        val indexExpr = expr.parse()

        if (TokenType.RIGHT_BRACKET != parser.token.kind) parser.flagError(ErrorCode.MISSING_RIGHT_BRACKET)
        parser.advanceCursor()

        return AccessIndexNode(indexExpr)
    }

    /**
     * Evaluates a call (e.g. 'foo()') and flag errors.
     */
    private fun evalCallAccess(): AccessCallNode {
        val expr = parserFactory.expressionParser
        val args = arrayListOf<INode>()

        // Is the argument list non empty?
        if (TokenType.RIGHT_PARENTHESIS != parser.token.kind) {
            parser.skipNewLines()
            args.add(expr.parse())
            while (TokenType.COMMA == parser.token.kind) {
                parser.advanceCursor()
                parser.skipNewLines()
                args.add(expr.parse())
            }
        }

        if (TokenType.RIGHT_PARENTHESIS != parser.token.kind) parser.flagError(ErrorCode.MISSING_RIGHT_PARENTHESIS)
        parser.advanceCursor()

        return AccessCallNode(args.toList())
    }
}