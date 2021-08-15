package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.IParser
import org.leaflang.parser.ast.BlockNode

/**
 * Evaluates the block semantics:
 *
 * <block> ::= '{' (NL)* <statements> (NL)* '}'
 *
 */
class BlockEval(private val parser: IParser) : IEval {
    override fun eval(): BlockNode {
        if (TokenType.LEFT_CURLY_BRACE != parser.token.kind) parser.flagError(ErrorCode.MISSING_BLOCK_LEFT_CURLY_BRACE)
        parser.advanceCursor()

        parser.skipNewLines()
        val result = BlockNode(StatementListEval(parser).eval())
        parser.skipNewLines()

        if (TokenType.RIGHT_CURLY_BRACE != parser.token.kind) parser.flagError(ErrorCode.MISSING_BLOCK_RIGHT_CURLY_BRACE)
        parser.advanceCursor()

        return result
    }
}