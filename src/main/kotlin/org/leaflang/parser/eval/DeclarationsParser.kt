package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.*
import org.leaflang.parser.ast.type.TypeNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the declaration semantics:
 *
 * <declarations> ::= <declaration> ((NL)* ',' (NL)* <declaration>)*
 * <declaration>  ::= <name> (NL)* (':' (NL)* <type>)? ('=' (NL)* <expr>)?
 *
 */
class DeclarationsParser(private val parser: ILeafParser,
                         private val parserFactory: IParserFactory,
                         private vararg val modifiers: Modifier) : IParser {

    override fun parse(): DeclarationsNode {
        val declarations = arrayListOf<Declaration>()
        while (true) {
            declarations.add(evalDeclaration())

            // There are no more variable declarations, break the loop and return the declaration node
            if (TokenType.COMMA != parser.token.kind) break
            parser.advanceCursor()
            parser.skipNewLines()
        }
        return DeclarationsNode(declarations, *modifiers)
    }

    /**
     * Evaluates the declaration statement.
     */
    private fun evalDeclaration(): Declaration {
        val typeParser = parserFactory.typeParser
        val expressionParser = parserFactory.expressionParser

        if (TokenType.IDENTIFIER != parser.token.kind) parser.flagError(ErrorCode.MISSING_IDENTIFIER)

        val id = parser.tokenAndAdvance.value as String
        var typeExpr: TypeNode? = null
        var assignExpr: INode? = null
        parser.skipNewLines()

        if (TokenType.COLON == parser.token.kind) {
            parser.advanceAndSkipNewLines { typeExpr = typeParser.parse() }
        }

        if (TokenType.ASSIGNMENT == parser.token.kind) {
            parser.advanceAndSkipNewLines { assignExpr = expressionParser.parse() }
        }

        if (assignExpr == null && typeExpr == null) {
            parser.flagError(ErrorCode.INVALID_VARIABLE_DECLARATION)
        }

        return Declaration(id, assignExpr, typeExpr)
    }
}