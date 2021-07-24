package org.nyxlang.parser

import org.nyxlang.lexer.token.CommentToken
import org.nyxlang.lexer.token.EndOfProgramToken
import org.nyxlang.lexer.token.IToken
import org.nyxlang.lexer.token.NewLineToken
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.eval.ProgramEval
import org.nyxlang.parser.exception.EvalException
import org.nyxlang.parser.exception.ParserException

class Parser : IParser {

    private var tokens = arrayOf<IToken>()
    private var cursorPosition = 0

    override fun parse(tokens: Array<IToken>): INode {
        this.tokens = tokens
                .filter { CommentToken::class != it::class }
                .toTypedArray()

        this.cursorPosition = 0
        try {
            return ProgramEval(this).eval()
        } catch (e: EvalException) {
            throw ParserException("Some semantic errors were detected during program parsing", arrayListOf(ParserError(e.message!!)))
        }
    }

    /**
     * Tests if a next token is available.
     */
    private fun hasNextToken() = cursorPosition + 1 < tokens.size

    override fun advanceCursor(by: Int): Int {
        cursorPosition += by
        return cursorPosition
    }

    override fun skipNewLines() {
        while (NewLineToken::class == token::class) {
            advanceCursor()
        }
    }

    override val token: IToken
        get() = if (cursorPosition >= tokens.size) {
            EndOfProgramToken()
        } else tokens[cursorPosition]

    override val peekNextToken: IToken
        get() = if (!hasNextToken()) {
            EndOfProgramToken()
        } else tokens[cursorPosition + 1]

}