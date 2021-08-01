package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.keyword.*
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.ast.BreakNode
import org.nyxlang.parser.ast.ContinueNode
import org.nyxlang.parser.ast.Modifier
import org.nyxlang.parser.ast.ReturnNode

/**
 * Evaluates the statement semantics:
 *
 * <statement>  ::= ('const' | 'var') (NL)* <declaration>
 *                | 'async' <statement>
 *                | 'return' ((NL)* <expr>)?
 *                | 'break'
 *                | 'continue'
 *                | <type-declaration>
 *                | <assignment>
 *                | <loop-stmt>
 *                | <expr>
 *
 */
class StatementEval(private val parser: IParser) : IEval {
    override fun eval() = when (parser.token::class) {
        ConstKeywordToken::class -> parser.advance { DeclarationsEval(parser, Modifier.CONSTANT).eval() }
        VarKeywordToken::class -> parser.advance { DeclarationsEval(parser).eval() }
        ReturnKeywordToken::class -> parser.advance { ReturnNode(ExprEval(parser).eval()) }
        BreakKeywordToken::class -> parser.advance { BreakNode() }
        ContinueKeywordToken::class -> parser.advance { ContinueNode() }
        LoopKeywordToken::class -> LoopEval(parser).eval()
        TypeKeywordToken::class -> TypeDeclarationEval(parser).eval()
        else -> ExprEval(parser).eval()
    }
}