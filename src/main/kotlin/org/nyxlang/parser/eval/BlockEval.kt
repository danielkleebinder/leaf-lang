package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.bracket.RightCurlyBraceToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.BlockNode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the block semantics:
 *
 * <block> ::= '{' <statement-list> '}'
 *
 */
class BlockEval(private val parser: IParser) : IEval {
    override fun eval(): BlockNode {
        if (LeftCurlyBraceToken::class != parser.token::class) throw EvalException("Block statement requires opening curly braces")
        parser.advanceCursor()

        val result = BlockNode(StatementListEval(parser).eval())

        if (RightCurlyBraceToken::class != parser.token::class) throw EvalException("Block statement requires closing curly braces")
        parser.advanceCursor()

        return result
    }
}