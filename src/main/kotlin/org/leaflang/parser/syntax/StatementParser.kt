package org.leaflang.parser.syntax

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advance
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.BreakNode
import org.leaflang.parser.ast.ContinueNode
import org.leaflang.parser.ast.ReturnNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the statement semantics:
 *
 * <statement>  ::= ('const' | 'var') (NL)* <declaration>
 *                | 'return' ((NL)* <expr>)?
 *                | 'break'
 *                | 'continue'
 *                | <type-declaration>
 *                | <assignment>
 *                | <use-stmt>
 *                | <loop-stmt>
 *                | <expr>
 *
 */
class StatementParser(private val parser: ILeafParser,
                      private val parserFactory: IParserFactory) : IParser {
    override fun parse() = when (parser.token.kind) {
        TokenType.KEYWORD_CONST -> parser.advanceAndSkipNewLines { parserFactory.constDeclarationsParser.parse() }
        TokenType.KEYWORD_VAR -> parser.advanceAndSkipNewLines { parserFactory.varDeclarationsParser.parse() }
        TokenType.KEYWORD_RETURN -> parser.advance { ReturnNode(parser.nodePosition(), parserFactory.expressionParser.parse()) }
        TokenType.KEYWORD_BREAK -> parser.advance { BreakNode(parser.nodePosition()) }
        TokenType.KEYWORD_CONTINUE -> parser.advance { ContinueNode(parser.nodePosition()) }
        TokenType.KEYWORD_USE -> parserFactory.useParser.parse()
        TokenType.KEYWORD_LOOP -> parserFactory.loopParser.parse()
        TokenType.KEYWORD_TRAIT -> parserFactory.traitDeclareParser.parse()
        TokenType.KEYWORD_TYPE -> parserFactory.typeDeclareParser.parse()
        else -> parserFactory.expressionParser.parse()
    }
}