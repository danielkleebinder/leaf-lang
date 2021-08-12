package org.nyxlang.parser

import org.nyxlang.error.*
import org.nyxlang.lexer.token.Token
import org.nyxlang.lexer.token.TokenType
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.eval.ProgramEval

class Parser(override var errorHandler: IErrorHandler? = null) : IParser {

    private var tokens = arrayOf<Token>()
    private var cursorPosition = 0

    override fun parse(tokens: Array<Token>): INode {
        this.cursorPosition = 0
        this.tokens = tokens
                .filter { TokenType.COMMENT != it.kind }
                .toTypedArray()

        return ProgramEval(this).eval()
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
        errorHandler?.flag(AnalysisError(errorCode, fromToken(token), ErrorType.SYNTAX))
    }

    override val token: Token
        get() = if (cursorPosition >= tokens.size) tokens.last() else tokens[cursorPosition]

    override val peekNextToken: Token
        get() = if (cursorPosition + 1 >= tokens.size) tokens.last() else tokens[cursorPosition + 1]

}