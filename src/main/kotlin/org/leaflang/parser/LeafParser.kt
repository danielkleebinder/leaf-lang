package org.leaflang.parser

import org.leaflang.error.*
import org.leaflang.lexer.token.Token
import org.leaflang.lexer.token.TokenType
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.LazyParserFactory

/**
 * Concrete implementation for the leaf programming language parser.
 */
class LeafParser(override var errorHandler: IErrorHandler? = null) : ILeafParser {

    private var tokens = arrayOf<Token>()
    private var cursorPosition = 0

    override fun parse(tokens: Array<Token>): INode {
        this.cursorPosition = 0
        this.tokens = tokens
                .filter { TokenType.COMMENT != it.kind }
                .toTypedArray()

        val parserFactory = LazyParserFactory(this)
        return parserFactory.programParser.parse()
    }

    override fun advanceCursor(by: Int): Int {
        cursorPosition += by
        return cursorPosition
    }

    override fun skipNewLines() {
        while (TokenType.NEW_LINE == token.kind) {
            advanceCursor()
        }
    }

    override fun flagError(errorCode: ErrorCode) {
        errorHandler?.handle(AnalysisError(errorCode, fromToken(token), ErrorType.SYNTAX))
    }

    override val token: Token
        get() = if (cursorPosition >= tokens.size) tokens.last() else tokens[cursorPosition]

    override val peekNextToken: Token
        get() = if (cursorPosition + 1 >= tokens.size) tokens.last() else tokens[cursorPosition + 1]

}