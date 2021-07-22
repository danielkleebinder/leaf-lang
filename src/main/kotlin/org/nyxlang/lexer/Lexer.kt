package org.nyxlang.lexer

import org.nyxlang.lexer.exception.LexerException
import org.nyxlang.lexer.exception.TokenizerException
import org.nyxlang.lexer.token.IToken
import org.nyxlang.lexer.tokenizer.*

class Lexer : ILexer {

    override var programCode: String? = null
        private set

    override var cursorPosition = 0
        private set

    override fun tokenize(program: String): Array<IToken> {
        programCode = program
        cursorPosition = 0

        val tokens = arrayListOf<IToken>()
        val errors = arrayListOf<LexerError>()

        while (!isEndOfProgram) {
            for (tokenizer in tokenizerRegistry) {
                if (!tokenizer.matches(symbol)) {
                    continue
                }
                try {
                    tokens.add(tokenizer.tokenize(this))
                    break
                } catch (e: TokenizerException) {
                    errors.add(LexerError(e.message, e.location))
                }
            }
            advanceCursor()
        }
        if (errors.size > 0) {
            throw LexerException("Some syntax errors were detected during lexical analysis", errors.toTypedArray())
        }
        return tokens.toTypedArray()
    }

    override val isEndOfProgram: Boolean
        get() = programCode == null || cursorPosition >= programCode!!.length

    override fun advanceCursor(by: Int): Int {
        checkNotNull(programCode) { "No program to advance cursor on" }
        return by.let { cursorPosition += it; cursorPosition }
    }

    override val symbol: Char
        get() {
            checkNotNull(programCode) { "No program to get symbol from" }
            return programCode!![cursorPosition]
        }

    override val peekNextSymbol: Char
        get() {
            checkNotNull(programCode) { "No program to get symbol from" }
            return programCode!![cursorPosition + 1]
        }

    companion object {
        private val tokenizerRegistry = arrayOf(
                NumberTokenizer(),
                NameTokenizer(),
                PlusTokenizer(),
                MinusTokenizer(),
                DivideTokenizer(),
                MultiplyTokenizer(),
                ModTokenizer(),
                ComplementTokenizer(),
                LogicalTokenizer(),
                BracketTokenizer(),
                CommaTokenizer(),
                ColonTokenizer(),
                DotTokenizer(),
                StatementTokenizer(),
                NewLineTokenizer(),
                StringTokenizer()
        )
    }
}