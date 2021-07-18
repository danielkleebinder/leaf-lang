package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.keyword.ConditionalKeywordToken
import org.nyxlang.lexer.token.keyword.ElseKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.ConditionalCase
import org.nyxlang.parser.ast.ConditionalNode
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.exception.EvalException

/**
 * Evaluates the conditional semantics:
 *
 * <conditional-expr> ::= 'if' <expr>         <block-stmt>
 *                        ('else' 'if' <expr> <block-stmt>)*
 *                        ('else'             <block-stmt>)?
 *
 */
class ConditionalEval(private val parser: IParser) : IEval {

    override fun eval(): ConditionalNode {
        val expr = ExprEval(parser)
        val statement = StatementEval(parser)

        val cases = arrayListOf<ConditionalCase>()
        var condition: INode? = null
        var body: INode
        var elseCase: INode? = null

        // Evaluate first if <expr> <block-stmt> block
        conditionalHead { condition = expr.eval() }
        cases.add(ConditionalCase(condition, statement.eval()))

        // Evaluate further case blocks
        while (ElseKeywordToken::class == parser.token::class) {
            parser.advanceCursor()

            if (ConditionalKeywordToken::class == parser.token::class) {
                parser.advanceCursor()

                condition = expr.eval()
                cases.add(ConditionalCase(condition, statement.eval()))
            } else {
                elseCase = statement.eval()
            }
        }

        return ConditionalNode(cases, elseCase)
    }

    /**
     * Evaluates the conditional head semantics and throws exceptions if semantics are incorrect or
     * executes the body if everything is fine.
     */
    private inline fun conditionalHead(head: () -> Unit) {
        if (ConditionalKeywordToken::class != parser.token::class) {
            throw EvalException("Conditional keyword 'if' expected")
        }
        parser.advanceCursor()

        if (LeftCurlyBraceToken::class != parser.token::class) {
            head()
        }
    }
}