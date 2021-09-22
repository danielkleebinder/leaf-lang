package org.leaflang.parser.syntax

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.access.AccessCallNode
import org.leaflang.parser.ast.access.AccessFieldNode
import org.leaflang.parser.ast.access.AccessIndexNode
import org.leaflang.parser.ast.access.AccessNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the additive semantics:
 *
 * <var> ::= <name>
 *         | <name> (NL)* '.' (NL)* <name>
 *         | <name> (NL)* '[' (NL)* <expr> (NL*) ']'
 *         | <name> (NL)* '(' (NL)* (<expr> ((NL)* ',' (NL)* <expr>))? (NL)* ')'
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

        val pos = parser.nodePosition()
        val id = parser.tokenAndAdvance.value as String
        val children = arrayListOf<INode>()

        var wasNewLine = TokenType.NEW_LINE == parser.token.kind
        parser.skipNewLines()

        while (childIdentifier.contains(parser.token.kind)) {
            when (parser.token.kind) {
                TokenType.DOT -> parser.advanceAndSkipNewLines { children.add(evalFieldAccess()) }
                TokenType.LEFT_BRACKET -> parser.advanceAndSkipNewLines { children.add(evalIndexAccess()) }
                TokenType.LEFT_PARENTHESIS -> parser.advanceAndSkipNewLines { children.add(evalCallAccess()) }
                else -> parser.flagError(ErrorCode.INVALID_ACCESS)
            }
            wasNewLine = TokenType.NEW_LINE == parser.token.kind
            parser.skipNewLines()
        }

        // This is a little bit tricky since I have to look "over" all
        // the new lines and check if the else keyword was found. If I
        // do not find an else keyword I have to undo the new line skipping.
        // Otherwise statements could not be separated properly.
        if (wasNewLine) parser.advanceCursor(-1)

        return AccessNode(pos, id, children.toList())
    }

    /**
     * Evaluates member field access (e.g. 'foo.bar') and flag errors.
     */
    private fun evalFieldAccess(): AccessFieldNode {
        val pos = parser.nodePosition()
        if (TokenType.IDENTIFIER != parser.token.kind) parser.flagError(ErrorCode.MISSING_IDENTIFIER)
        val name = parser.tokenAndAdvance.value as String
        return AccessFieldNode(pos, name)
    }

    /**
     * Evaluates index based access (e.g. 'foo[10]') and flag errors.
     */
    private fun evalIndexAccess(): AccessIndexNode {
        val expr = parserFactory.expressionParser
        val pos = parser.nodePosition()
        val indexExpr = expr.parse()

        if (TokenType.RIGHT_BRACKET != parser.token.kind) parser.flagError(ErrorCode.MISSING_RIGHT_BRACKET)
        parser.advanceCursor()

        return AccessIndexNode(pos, indexExpr)
    }

    /**
     * Evaluates a call (e.g. 'foo()') and flag errors.
     */
    private fun evalCallAccess(): AccessCallNode {
        val expr = parserFactory.expressionParser
        val args = arrayListOf<INode>()
        val pos = parser.nodePosition()

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

        return AccessCallNode(pos, args.toList())
    }
}