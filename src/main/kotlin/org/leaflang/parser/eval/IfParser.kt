package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.IfCase
import org.leaflang.parser.ast.IfNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the 'if' syntax:
 *
 * <if-expr> ::= 'if' ((NL)* <expr> (NL)* <block>)
 *                    ((NL)* <else-if>)*
 *                    ((NL)* <else>)?
 *
 * <else-if> ::= 'else' (NL)* 'if' (NL)* <expr> (NL)* <block>
 * <else>    ::= 'else' (NL)*                         <block>
 *
 */
class IfParser(private val parser: ILeafParser,
               private val parserFactory: IParserFactory) : IParser {

    override fun parse(): IfNode {
        val expr = parserFactory.expressionParser
        val block = parserFactory.blockParser

        val pos = parser.nodePosition()
        val cases = arrayListOf<IfCase>()
        var condition: INode? = null
        var elseCase: INode? = null

        // Evaluate first 'if' ((NL)* <expr> (NL)* <block>) block
        ifHead { condition = expr.parse() }
        cases.add(IfCase(condition, block.parse()))

        var wasNewLine = TokenType.NEW_LINE == parser.token.kind
        parser.skipNewLines()

        // Evaluate further case blocks
        while (TokenType.KEYWORD_ELSE == parser.token.kind) {
            parser.advanceCursor()
            parser.skipNewLines()

            if (TokenType.KEYWORD_IF == parser.token.kind) {
                parser.advanceCursor()
                parser.skipNewLines()

                condition = expr.parse()
                cases.add(IfCase(condition, block.parse()))
            } else {
                elseCase = block.parse()
            }
            wasNewLine = TokenType.NEW_LINE == parser.token.kind
            parser.skipNewLines()
        }

        // This is a little bit tricky since I have to look "over" all
        // the new lines and check if the else keyword was found. If I
        // do not find an else keyword I have to undo the new line skipping.
        // Otherwise statements could not be separated properly.
        if (wasNewLine) parser.advanceCursor(-1)

        return IfNode(pos, cases, elseCase)
    }

    /**
     * Evaluates the conditional head semantics and throws exceptions if semantics are incorrect or
     * executes the body if everything is fine.
     */
    private inline fun ifHead(head: () -> Unit) {
        if (TokenType.KEYWORD_IF != parser.token.kind) {
            parser.flagError(ErrorCode.MISSING_KEYWORD_IF)
        }
        parser.advanceCursor()

        if (TokenType.LEFT_CURLY_BRACE != parser.token.kind) {
            parser.skipNewLines()
            head()
        }
        parser.skipNewLines()
    }
}