package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.bracket.RightCurlyBraceToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.BlockNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the block semantics:
 *
 * <block> ::= '{' (NL)* <statements> (NL)* '}'
 *
 */
class BlockEval(private val parser: IParser) : IEval {
    override fun eval(): BlockNode {
        if (LeftCurlyBraceToken::class != parser.token::class) throw EvalException("Block statement requires opening curly braces")
        parser.advanceCursor()

        parser.skipNewLines()
        val result = BlockNode(StatementListEval(parser).eval())
        parser.skipNewLines()

        if (RightCurlyBraceToken::class != parser.token::class) throw EvalException("Block statement requires closing curly braces")
        parser.advanceCursor()

        return result
    }
}