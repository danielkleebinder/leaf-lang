package org.leaflang.parser.syntax

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.UseNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the use declaration syntax:
 *
 * 'use' (NL)* <string>
 *
 */
class UseParser(private val parser: ILeafParser,
                private val parserFactory: IParserFactory) : IParser {

    override fun parse(): UseNode {
        val pos = parser.nodePosition()

        // Is this even a use declaration?
        if (TokenType.KEYWORD_USE != parser.token.kind) parser.flagError(ErrorCode.MISSING_KEYWORD_USE)
        parser.advanceCursor()
        parser.skipNewLines()

        var name = "<undefined>"
        if (TokenType.STRING == parser.token.kind) {
            name = parser.tokenAndAdvance.value as String
        } else {
            parser.flagError(ErrorCode.MISSING_USE_FILE_PATH)
        }
        return UseNode(pos, name)
    }
}