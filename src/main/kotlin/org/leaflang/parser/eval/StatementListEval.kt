package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.IParser
import org.leaflang.parser.ast.EmptyNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.StatementListNode

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
        while (TokenType.SEPARATOR == parser.token.kind ||
                TokenType.NEW_LINE == parser.token.kind) {
            parser.advanceCursor()
            parser.skipNewLines()

            val evaluated = statement.eval()
            if (EmptyNode::class != evaluated::class) result.add(evaluated)
        }
        return StatementListNode(result)
    }
}