package org.leaflang.parser.eval

import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.ProgramNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the program semantics:
 *
 * <program> ::= <statements>
 *
 */
class ProgramParser(private val parser: ILeafParser,
                    private val parserFactory: IParserFactory) : IParser {
    override fun parse() = ProgramNode(parserFactory.statementListParser.parse())
}