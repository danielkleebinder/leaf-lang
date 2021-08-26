package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.AssignmentNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.IParserFactory
import org.leaflang.parser.utils.fromToken

/**
 * Evaluates the assignment semantics:
 *
 * <assignment> ::= <var> ('=' <expr>)?
 *
 */
class AssignmentParser(private val parser: ILeafParser,
                       private val parserFactory: IParserFactory) : IParser {

    override fun parse(): INode {
        val varParser = parserFactory.variableParser
        val expr = parserFactory.expressionParser
        val pos = fromToken(parser.token)

        if (TokenType.IDENTIFIER != parser.token.kind) parser.flagError(ErrorCode.MISSING_IDENTIFIER)

        val variable = varParser.parse()

        if (TokenType.ASSIGNMENT == parser.token.kind) {
            parser.advanceCursor()
            parser.skipNewLines()

            val assignmentExpr = expr.parse()
            return AssignmentNode(pos, variable, assignmentExpr)
        }
        return variable
    }
}