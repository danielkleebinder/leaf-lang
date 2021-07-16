package org.pl.parser

import org.pl.lexer.token.EndOfProgramToken
import org.pl.lexer.token.IToken
import org.pl.parser.ast.INode
import org.pl.parser.eval.ProgramEval
import org.pl.parser.exception.EvalException
import org.pl.parser.exception.ParserException

class Parser : IParser {

    private var tokens = arrayOf<IToken>()
    private var cursorPosition = 0

    override fun parse(tokens: Array<IToken>): INode {
        this.tokens = tokens
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
    private fun hasNextToken() = cursorPosition < tokens.size

    override fun advanceCursor(by: Int): Int {
        cursorPosition += by
        return cursorPosition
    }

    override val token: IToken
        get() = if (!hasNextToken()) {
            EndOfProgramToken()
        } else tokens[cursorPosition]

    override val peekNextToken: IToken
        get() = if (!hasNextToken()) {
            EndOfProgramToken()
        } else tokens[cursorPosition + 1]

}