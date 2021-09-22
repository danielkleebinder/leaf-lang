package org.leaflang.parser.syntax

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.UseNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the use declaration syntax:
 *
 * <use-stmt> ::= 'use' (NL)* '(' (NL)* <string> (NL)* (',' (NL)* <string> (NL)*)*  ')'
 *
 */
class UseParser(private val parser: ILeafParser,
                private val parserFactory: IParserFactory) : IParser {

    override fun parse(): UseNode {
        val pos = parser.nodePosition()

        // Is this even a use declaration?
        if (TokenType.KEYWORD_USE != parser.token.kind) parser.flagError(ErrorCode.MISSING_KEYWORD_USE)
        val inFile = parser.tokenAndAdvance.value as String
        parser.skipNewLines()

        // The list of imported files must be in parenthesis
        if (TokenType.LEFT_PARENTHESIS != parser.token.kind) parser.flagError(ErrorCode.MISSING_LEFT_PARENTHESIS)
        parser.advanceCursor()
        parser.skipNewLines()

        val loadFiles = arrayListOf<String>()
        if (TokenType.STRING == parser.token.kind) {
            loadFiles.add(parser.tokenAndAdvance.value as String)
            parser.skipNewLines()
        }

        // Load the entire list of use statements
        while (TokenType.COMMA == parser.token.kind) {
            parser.advanceCursor()
            parser.skipNewLines()
            if (TokenType.STRING == parser.token.kind) {
                loadFiles.add(parser.tokenAndAdvance.value as String)
                parser.skipNewLines()
            } else {
                parser.flagError(ErrorCode.MISSING_USE_FILE_PATH)
            }
        }

        // Closing parenthesis are required
        if (TokenType.RIGHT_PARENTHESIS != parser.token.kind) parser.flagError(ErrorCode.MISSING_RIGHT_PARENTHESIS)
        parser.advanceCursor()

        return UseNode(pos, inFile, loadFiles.toList())
    }
}