package org.pl.parser.eval

import org.pl.lexer.token.AssignToken
import org.pl.lexer.token.NameToken
import org.pl.lexer.token.keyword.ConstKeywordToken
import org.pl.lexer.token.keyword.VarKeywordToken
import org.pl.parser.IParser
import org.pl.parser.ast.INode
import org.pl.parser.ast.Modifier


/**
 * Evaluates the statement semantics:
 *
 * <statement> ::= ('var' | 'const') <var-declare>
 *               | <var-assign>
 *               | <fun-declare>
 *               | <expr>
 *
 */
class StatementEval(private val parser: IParser) : IEval {
    override fun eval(): INode {
        if (VarKeywordToken::class == parser.token::class) {
            parser.advanceCursor()
            return VarDeclareEval(parser).eval()
        }

        if (ConstKeywordToken::class == parser.token::class) {
            parser.advanceCursor()
            return VarDeclareEval(parser).eval().also { it.modifiers.add(Modifier.CONSTANT) }
        }

        if (NameToken::class == parser.token::class &&
                AssignToken::class == parser.peekNextToken::class) {
            return VarAssignEval(parser).eval()
        }
        return ExprEval(parser).eval()
    }
}