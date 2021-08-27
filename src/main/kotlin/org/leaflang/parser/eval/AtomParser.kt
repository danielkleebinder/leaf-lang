package org.leaflang.parser.eval

import org.leaflang.error.ErrorCode
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.advance
import org.leaflang.parser.advanceAndSkipNewLines
import org.leaflang.parser.ast.AsyncNode
import org.leaflang.parser.ast.EmptyNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.value.BoolNode
import org.leaflang.parser.ast.value.NumberNode
import org.leaflang.parser.ast.value.StringNode
import org.leaflang.parser.utils.IParserFactory
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
class AtomParser(private val parser: ILeafParser,
                 private val parserFactory: IParserFactory) : IParser {

    private val assignmentParser = parserFactory.assignmentParser
    private val arrayExpressionParser = parserFactory.arrayExpressionParser
    private val ifParser = parserFactory.ifParser
    private val funDeclarationParser = parserFactory.funDeclareParser
    private val typeInstantiationParser = parserFactory.typeInstantiationParser
    private val blockParser = parserFactory.blockParser
    private val statementParser = parserFactory.statementParser
    private val expressionParser = parserFactory.expressionParser

    override fun parse(): INode {
        val pos = parser.nodePosition()
        return when (parser.token.kind) {

            TokenType.BOOL -> BoolNode(pos, parser.tokenAndAdvance.value as Boolean)
            TokenType.STRING -> StringNode(pos, parser.tokenAndAdvance.value as String)
            TokenType.NUMBER -> NumberNode(pos, BigDecimal((parser.tokenAndAdvance.value as Double).toString()
                    .trimEnd('0')
                    .trimEnd('.')))

            TokenType.IDENTIFIER -> assignmentParser.parse()
            TokenType.LEFT_BRACKET -> arrayExpressionParser.parse()
            TokenType.KEYWORD_IF -> ifParser.parse()
            TokenType.KEYWORD_FUN -> funDeclarationParser.parse()
            TokenType.KEYWORD_NEW -> typeInstantiationParser.parse()
            TokenType.LEFT_CURLY_BRACE -> blockParser.parse()

            TokenType.KEYWORD_ASYNC -> parser.advance { AsyncNode(pos, statementParser.parse()) }
            TokenType.LEFT_PARENTHESIS -> parser.advanceAndSkipNewLines {
                val result = expressionParser.parse()
                parser.skipNewLines()
                if (TokenType.RIGHT_PARENTHESIS != parser.token.kind) parser.flagError(ErrorCode.MISSING_RIGHT_PARENTHESIS)
                parser.advanceCursor()
                result
            }

            TokenType.ERROR -> {
                parser.flagError(parser.token.value as ErrorCode)
                EmptyNode(pos)
            }

            else -> EmptyNode(pos)
        }
    }
}
