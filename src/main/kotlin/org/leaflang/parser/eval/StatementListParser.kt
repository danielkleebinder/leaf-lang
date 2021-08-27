package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.EmptyNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.StatementListNode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the statement list semantics:
 *
 * <statements> ::= <statement> ((';' | (NL)*) <statement>)*
 *
 */
class StatementListParser(private val parser: ILeafParser,
                          private val parserFactory: IParserFactory) : IParser {

    override fun parse(): StatementListNode {
        val statementParser = parserFactory.statementParser
        val pos = parser.nodePosition()
        val result = arrayListOf<INode>()
        result.add(statementParser.parse())
        while (TokenType.SEPARATOR == parser.token.kind ||
                TokenType.NEW_LINE == parser.token.kind) {
            parser.advanceCursor()
            parser.skipNewLines()

            val evaluated = statementParser.parse()
            if (EmptyNode::class != evaluated::class) result.add(evaluated)
        }
        return StatementListNode(pos, result)
    }
}