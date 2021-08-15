package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.IParser
import org.leaflang.parser.advance
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.*
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
            TokenType.STRING -> StringNode(parser.tokenAndAdvance.value as String)
            TokenType.NUMBER -> NumberNode(BigDecimal((parser.tokenAndAdvance.value as Double).toString()
                    .trimEnd('0')
                    .trimEnd('.')))

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