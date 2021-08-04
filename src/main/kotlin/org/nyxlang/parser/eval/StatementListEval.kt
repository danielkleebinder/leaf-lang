package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.EmptyNode
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