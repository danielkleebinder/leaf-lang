package org.leaflang.lexer

import org.leaflang.RuntimeOptions
import org.leaflang.lexer.source.ISource
import org.leaflang.lexer.token.Token
import org.leaflang.lexer.token.TokenFactory
import org.leaflang.lexer.token.TokenType
import org.leaflang.lexer.tokenizer.*

/**
 * Concrete lexer implementation.
 */
class Lexer : ILexer {

    companion object {
        private val tokenizerRegistry = arrayOf(
                NumberTokenizer(),
                NameTokenizer(),
                PlusTokenizer(),
                MinusTokenizer(),
                SlashTokenizer(),
                TimesTokenizer(),
                RemainderTokenizer(),
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

    override fun tokenize(source: ISource): Array<Token> {
        val tokenFactory = TokenFactory(source)
        val tokens = arrayListOf<Token>()

        while (!source.isEndOfProgram) {
            var token: Token? = null
            for (tokenizer in tokenizerRegistry) {
                if (!tokenizer.matches(source.symbol)) continue
                token = tokenizer.tokenize(source, tokenFactory)
                break
            }
            if (token == null) {

                // No token matched and I have to skip this character
                if (RuntimeOptions.debug) println("Skip character \"${source.symbol}\" in lexical analysis (no tokenizer matched)")
                source.advanceCursor()
            } else {
                tokens.add(token)
            }
        }

        // Finish with end of program token
        tokens.add(tokenFactory.newToken(TokenType.END_OF_PROGRAM))
        return tokens.toTypedArray()
    }
}