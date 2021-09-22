package org.leaflang.parser.syntax

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.type.TraitDeclareNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the trait declaration syntax:
 *
 * <trait-declaration> ::= 'trait' (NL)* <name> (NL)*
 *
 */
class TraitDeclareParser(private val parser: ILeafParser,
                         private val parserFactory: IParserFactory) : IParser {

    override fun parse(): TraitDeclareNode {
        val pos = parser.nodePosition()

        // Is this even a trait declaration?
        if (TokenType.KEYWORD_TRAIT != parser.token.kind) parser.flagError(ErrorCode.MISSING_KEYWORD_TRAIT)
        parser.advanceCursor()
        parser.skipNewLines()

        var name = "<anonymous>"
        if (TokenType.IDENTIFIER == parser.token.kind) {
            name = parser.tokenAndAdvance.value as String
        } else {
            parser.flagError(ErrorCode.MISSING_IDENTIFIER)
        }

        if (TokenType.LEFT_CURLY_BRACE == parser.token.kind) {
            parser.flagError(ErrorCode.INVALID_TRAIT_CURLY_BRACE)
        }

        return TraitDeclareNode(pos, name)
    }
}