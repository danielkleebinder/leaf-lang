package org.nyxlang.parser.eval

import org.nyxlang.error.ErrorCode
import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.IfCase
import org.nyxlang.parser.ast.IfNode

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
class IfEval(private val parser: IParser) : IEval {

    override fun eval(): IfNode {
        val expr = ExprEval(parser)
        val block = BlockEval(parser)

        val cases = arrayListOf<IfCase>()
        var condition: INode? = null
        var elseCase: INode? = null

        // Evaluate first 'if' ((NL)* <expr> (NL)* <block>) block
        ifHead { condition = expr.eval() }
        cases.add(IfCase(condition, block.eval()))

        var wasNewLine = TokenType.NEW_LINE == parser.token.kind
        parser.skipNewLines()

        // Evaluate further case blocks
        while (TokenType.KEYWORD_ELSE == parser.token.kind) {
            parser.advanceCursor()
            parser.skipNewLines()

            if (TokenType.KEYWORD_IF == parser.token.kind) {
                parser.advanceCursor()
                parser.skipNewLines()

                condition = expr.eval()
                cases.add(IfCase(condition, block.eval()))
            } else {
                elseCase = block.eval()
            }
            wasNewLine = TokenType.NEW_LINE == parser.token.kind
            parser.skipNewLines()
        }

        // This is a little bit tricky since I have to look "over" all
        // the new lines and check if the else keyword was found. If I
        // do not find an else keyword I have to undo the new line skipping.
        // Otherwise statements could not be separated properly.
        if (wasNewLine) parser.advanceCursor(-1)

        return IfNode(cases, elseCase)
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