package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.bracket.LeftCurlyBraceToken
import org.nyxlang.lexer.token.keyword.*
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.*
import org.nyxlang.parser.eval.expression.ExprEval

/**
 * Evaluates the statement semantics:
 *
 * <statement>  ::= ('const' | 'var') (NL)* <declaration>
 *                | 'return' ((NL)* <expr>)?
 *                | 'break'
 *                | 'continue'
 *                | <block>
 *                | <expr>
 *
 */
class StatementEval(private val parser: IParser) : IEval {
    override fun eval(): INode {
        when (parser.token::class) {
            ConstKeywordToken::class -> parser.advance {
                return DeclarationsEval(parser).eval()
                        .also { it.modifiers.add(Modifier.CONSTANT) }
            }
            VarKeywordToken::class -> parser.advance { return DeclarationsEval(parser).eval() }
            AsyncKeywordToken::class -> parser.advance { return AsyncNode(StatementEval(parser).eval()) }
            ReturnKeywordToken::class -> parser.advance { return ReturnNode(ExprEval(parser).eval()) }
            BreakKeywordToken::class -> parser.advance { return BreakNode() }
            ContinueKeywordToken::class -> parser.advance { return ContinueNode() }
            LeftCurlyBraceToken::class -> return BlockEval(parser).eval()
        }
        return ExprEval(parser).eval()
    }
}