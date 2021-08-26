package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.BlockNode
import org.leaflang.parser.utils.IParserFactory
import org.leaflang.parser.utils.fromToken

/**
 * Evaluates the block semantics:
 *
 * <block> ::= '{' (NL)* <statements> (NL)* '}'
 *
 */
class BlockParser(private val parser: ILeafParser,
                  private val parserFactory: IParserFactory) : IParser {

    override fun parse(): BlockNode {
        val statementListParser = parserFactory.statementListParser
        val pos = fromToken(parser.token)

        if (TokenType.LEFT_CURLY_BRACE != parser.token.kind) parser.flagError(ErrorCode.MISSING_BLOCK_LEFT_CURLY_BRACE)
        parser.advanceCursor()

        parser.skipNewLines()
        val result = BlockNode(pos, statementListParser.parse())
        parser.skipNewLines()

        if (TokenType.RIGHT_CURLY_BRACE != parser.token.kind) parser.flagError(ErrorCode.MISSING_BLOCK_RIGHT_CURLY_BRACE)
        parser.advanceCursor()

        return result
    }
}