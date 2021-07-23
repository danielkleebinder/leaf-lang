package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.bracket.LeftParenthesisToken
import org.nyxlang.lexer.token.keyword.*
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.*


/**
 * Evaluates the statement semantics:
 *
 * <statement> ::= ('var' | 'const') <var-declare>
 *               | 'return' (<expr>)?
 *               | 'break'
 *               | 'continue'
 *               | <block-stmt>
 *               | <fun-declare>
 *               | <fun-call>
 *               | <var-assign>
 *               | <expr>
 *
 */
class StatementEval(private val parser: IParser) : IEval {
    override fun eval(): INode {
        when (parser.token::class) {
            ConstKeywordToken::class -> parser.advance {
                return VarDeclareEval(parser).eval()
                        .also { it.modifiers.add(Modifier.CONSTANT) }
            }
            VarKeywordToken::class -> parser.advance { return VarDeclareEval(parser).eval() }
            FunKeywordToken::class -> return FunDeclareEval(parser).eval()
            ReturnKeywordToken::class -> parser.advance { return ReturnNode(ExprEval(parser).eval()) }
            BreakKeywordToken::class -> parser.advance { return BreakNode() }
            ContinueKeywordToken::class -> parser.advance { return ContinueNode() }
            LeftCurlyBraceToken::class -> return BlockEval(parser).eval()
        }

        if (NameToken::class == parser.token::class) {
            when (parser.peekNextToken::class) {
                AssignToken::class -> return VarAssignEval(parser).eval()
                LeftParenthesisToken::class -> return CallEval(parser).eval()
            }
        }
        return ExprEval(parser).eval()
    }
}