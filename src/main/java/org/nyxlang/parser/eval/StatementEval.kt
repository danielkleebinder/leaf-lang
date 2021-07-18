package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.keyword.ConstKeywordToken
import org.nyxlang.lexer.token.keyword.FunKeywordToken
import org.nyxlang.lexer.token.keyword.VarKeywordToken
import org.nyxlang.parser.IParser
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.Modifier


/**
 * Evaluates the statement semantics:
 *
 * <statement> ::= ('var' | 'const') <var-declare>
 *               | <fun-declare>
 *               | <var-assign>
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

        if (FunKeywordToken::class == parser.token::class) {
            return FunDeclareEval(parser).eval()
        }

        if (NameToken::class == parser.token::class) {
            when (parser.peekNextToken::class) {
                AssignToken::class -> return VarAssignEval(parser).eval()
                LeftParenthesisToken::class -> return FunCallEval(parser).eval()
            }
        }
        return ExprEval(parser).eval()
    }
}