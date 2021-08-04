package org.nyxlang.parser.eval

import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.advanceAndSkipNewLines
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
    override fun eval() = when (parser.token.kind) {
        TokenType.KEYWORD_CONST -> parser.advanceAndSkipNewLines { DeclarationsEval(parser, Modifier.CONSTANT).eval() }
        TokenType.KEYWORD_VAR -> parser.advanceAndSkipNewLines { DeclarationsEval(parser).eval() }
        TokenType.KEYWORD_RETURN -> parser.advance { ReturnNode(ExprEval(parser).eval()) }
        TokenType.KEYWORD_BREAK -> parser.advance { BreakNode() }
        TokenType.KEYWORD_CONTINUE -> parser.advance { ContinueNode() }
        TokenType.KEYWORD_LOOP -> LoopEval(parser).eval()
        TokenType.KEYWORD_TYPE -> TypeDeclarationEval(parser).eval()
        else -> ExprEval(parser).eval()
    }
}