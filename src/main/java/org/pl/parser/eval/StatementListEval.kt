package org.pl.parser.eval

import org.pl.lexer.token.StatementSeparatorToken
import org.pl.parser.IParser
import org.pl.parser.ast.INode
import org.pl.parser.ast.StatementListNode

/**
 * Evaluates the statement list semantics:
 *
 * <statement-list> ::= <statement>
 *                    | <statement> ';' <statement-list>
 *
 */
class StatementListEval(private val parser: IParser) : IEval {

    override fun eval(): StatementListNode {
        val statement = StatementEval(parser)

        val result = arrayListOf<INode>()
        result.add(statement.eval())
        while (StatementSeparatorToken::class == parser.token::class) {
            parser.advanceCursor()
            result.add(statement.eval())
        }
        return StatementListNode(result)
    }
}