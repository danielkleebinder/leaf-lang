package org.nyxlang.parser.eval

import org.nyxlang.error.ErrorCode
import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.IParser
import org.nyxlang.parser.advance
import org.nyxlang.parser.advanceAndSkipNewLines
import org.nyxlang.parser.ast.*
import java.math.BigDecimal

/**
 * Evaluates atoms with the following semantic:
 *
 * <atom> ::= <bool> | <number> | <string> | <name>
 *          | '(' <expr> ')'
 *          | <type-inst>
 *          | <arr-expr>
 *          | <if-expr>
 *          | <fun-declaration>
 *          | <coroutine>
 *          | <block>
 *          | <empty>
 *
 */
class AtomEval(private val parser: IParser) : IEval {

    override fun eval(): INode {
        return when (parser.token.kind) {

            TokenType.BOOL -> BoolNode(parser.tokenAndAdvance.value as Boolean)
            TokenType.NUMBER -> NumberNode(BigDecimal.valueOf(parser.tokenAndAdvance.value as Double))
            TokenType.STRING -> StringNode(parser.tokenAndAdvance.value as String)

            TokenType.IDENTIFIER -> AssignmentEval(parser).eval()
            TokenType.LEFT_BRACKET -> ArrayExprEval(parser).eval()
            TokenType.KEYWORD_IF -> IfEval(parser).eval()
            TokenType.KEYWORD_FUN -> FunDeclarationEval(parser).eval()
            TokenType.KEYWORD_NEW -> TypeInstantiationEval(parser).eval()
            TokenType.LEFT_CURLY_BRACE -> BlockEval(parser).eval()

            TokenType.KEYWORD_ASYNC -> parser.advance { AsyncNode(StatementEval(parser).eval()) }
            TokenType.LEFT_PARENTHESIS -> parser.advanceAndSkipNewLines {
                val result = ExprEval(parser).eval()
                parser.skipNewLines()
                if (TokenType.RIGHT_PARENTHESIS != parser.token.kind) parser.flagError(ErrorCode.MISSING_RIGHT_PARENTHESIS)
                parser.advanceCursor()
                result
            }

            else -> EmptyNode()
        }
    }
}
