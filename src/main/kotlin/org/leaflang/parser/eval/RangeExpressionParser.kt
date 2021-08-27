package org.leaflang.parser.eval

import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ILeafParser
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.IParserFactory

/**
 * Evaluates the range semantics:
 *
 * <ran-expr> ::= <add-expr> ((NL)* ( '..' ) (NL)* <add-expr>)*
 *
 */
class RangeExpressionParser(private val parser: ILeafParser,
                            private val parserFactory: IParserFactory) : IParser {

    override fun parse(): INode {
        val pos = parser.nodePosition()
        var node = parserFactory.additiveExpressionParser.parse()
        while (true) {
            node = when (parser.token.kind) {
                TokenType.RANGE -> TODO("Range is not supported yet")
                else -> break
            }
        }
        return node
    }
}