package org.pl.lexer.tokenizer

import org.pl.lexer.ILexer
import org.pl.lexer.token.BoolToken
import org.pl.lexer.token.IToken
import org.pl.lexer.token.NameToken
import org.pl.lexer.token.keyword.*

/**
 * Tokenizes names and reserved keywords. If no keyword matches, a name token
 * is returned.
 */
class NameTokenizer : ITokenizer {

    override fun matches(c: Char) = c.isLetter()

    override fun tokenize(lexer: ILexer): IToken {
        val nameBuilder = StringBuilder()
        while (!lexer.isEndOfProgram && matches(lexer.symbol)) {
            nameBuilder.append(lexer.symbol)
            lexer.advanceCursor()
        }
        lexer.advanceCursor(-1)
        val name = nameBuilder.toString()
        when (name) {
            "var" -> return VarKeywordToken()
            "const" -> return ConstKeywordToken()
            "if" -> return ConditionalKeywordToken()
            "else" -> return ElseKeywordToken()
            "true" -> return BoolToken(true)
            "false" -> return BoolToken(false)
            "fun" -> return FunctionKeywordToken()
            "loop" -> return LoopKeywordToken()
            "break" -> return BreakKeywordToken()
            "continue" -> return ContinueKeywordToken()
            "number" -> return NumberKeywordToken()
            "bool" -> return BoolKeywordToken()
        }
        return NameToken(name)
    }
}