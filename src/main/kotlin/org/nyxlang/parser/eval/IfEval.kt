package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.keyword.ElseKeywordToken
import org.nyxlang.lexer.token.keyword.IfKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.IfNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.IfCase
import org.nyxlang.parser.eval.expression.ExprEval
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the if semantics:
 *
 * <if-expr> ::= 'if' ((NL)* <expr> (NL)* <block>)
 *                    ((NL)* <else-if>)*
 *                    ((NL)* <else>)?
 *
 * <else-if> ::= 'else' (NL)* 'if' (NL)* <expr> (NL)* <block>
 * <else>    ::= 'else' (NL)*                         <block>
 *
 */
class IfEval(private val parser: IParser) : IEval {

    override fun eval(): IfNode {
        val expr = ExprEval(parser)
        val block = BlockEval(parser)

        val cases = arrayListOf<IfCase>()
        var condition: INode? = null
        var elseCase: INode? = null

        // Evaluate first 'if' ((NL)* <expr> (NL)* <block>) block
        ifHead { condition = expr.eval() }
        cases.add(IfCase(condition, block.eval()))

        // Evaluate further case blocks
        while (ElseKeywordToken::class == parser.token::class) {
            parser.advanceCursor()
            parser.skipNewLines()

            if (IfKeywordToken::class == parser.token::class) {
                parser.advanceCursor()
                parser.skipNewLines()

                condition = expr.eval()
                cases.add(IfCase(condition, block.eval()))
            } else {
                elseCase = block.eval()
            }
            parser.skipNewLines()
        }

        return IfNode(cases, elseCase)
    }

    /**
     * Evaluates the conditional head semantics and throws exceptions if semantics are incorrect or
     * executes the body if everything is fine.
     */
    private inline fun ifHead(head: () -> Unit) {
        if (IfKeywordToken::class != parser.token::class) {
            throw EvalException("Conditional keyword 'if' expected")
        }
        parser.advanceCursor()

        if (LeftCurlyBraceToken::class != parser.token::class) {
            parser.skipNewLines()
            head()
        }
        parser.skipNewLines()
    }
}