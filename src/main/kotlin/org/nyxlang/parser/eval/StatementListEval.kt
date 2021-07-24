package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.NewLineToken
import org.nyxlang.lexer.token.StatementSeparatorToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.StatementListNode

/**
 * Evaluates the statement list semantics:
 *
 * <statements> ::= <statement> ((';' | (NL)*) <statement>)*
 *
 */
class StatementListEval(private val parser: IParser) : IEval {

    override fun eval(): StatementListNode {
        val statement = StatementEval(parser)

        val result = arrayListOf<INode>()
        result.add(statement.eval())
        while (StatementSeparatorToken::class == parser.token::class ||
                NewLineToken::class == parser.token::class) {
            parser.advanceCursor()
            parser.skipNewLines()
            result.add(statement.eval())
        }
        return StatementListNode(result)
    }
}