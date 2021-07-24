package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.ILexer
import org.nyxlang.lexer.token.BoolToken
import org.nyxlang.lexer.token.IToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.keyword.*

/**
 * Tokenizes names and reserved keywords. If no keyword matches, a name token
 * is returned.
 */
class NameTokenizer : ITokenizer {

    override fun matches(c: Char) = c.isJavaIdentifierStart()

    override fun tokenize(lexer: ILexer): IToken {
        val nameBuilder = StringBuilder()
        while (!lexer.isEndOfProgram && lexer.symbol.isJavaIdentifierPart()) {
            nameBuilder.append(lexer.symbol)
            lexer.advanceCursor()
        }
        lexer.advanceCursor(-1)
        val name = nameBuilder.toString()
        when (name) {
            "var" -> return VarKeywordToken()
            "const" -> return ConstKeywordToken()
            "if" -> return IfKeywordToken()
            "else" -> return ElseKeywordToken()
            "true" -> return BoolToken(true)
            "false" -> return BoolToken(false)
            "fun" -> return FunKeywordToken()
            "loop" -> return LoopKeywordToken()
            "break" -> return BreakKeywordToken()
            "continue" -> return ContinueKeywordToken()
            "return" -> return ReturnKeywordToken()
            "number" -> return NumberKeywordToken()
            "bool" -> return BoolKeywordToken()
            "string" -> return StringKeywordToken()
            "when" -> return WhenKeywordToken()
        }
        return NameToken(name)
    }
}