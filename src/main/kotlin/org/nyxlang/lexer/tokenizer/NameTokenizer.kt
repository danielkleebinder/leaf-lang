package org.nyxlang.lexer.tokenizer

import org.nyxlang.lexer.source.ISource
import org.nyxlang.lexer.token.ITokenFactory
import org.nyxlang.lexer.token.Token
import org.nyxlang.lexer.token.TokenType

/**
 * Tokenizes names and reserved keywords. If no keyword matches, a name token
 * is returned.
 */
class NameTokenizer : ITokenizer {

    override fun matches(c: Char) = c.isJavaIdentifierStart()

    override fun tokenize(source: ISource, tokenFactory: ITokenFactory): Token {
        val nameBuilder = StringBuilder()
        while (!source.isEndOfProgram && source.symbol.isJavaIdentifierPart()) {
            nameBuilder.append(source.symbol)
            source.advanceCursor()
        }
        return when (val name = nameBuilder.toString()) {
            "var" -> tokenFactory.newToken(TokenType.KEYWORD_VAR)
            "const" -> tokenFactory.newToken(TokenType.KEYWORD_CONST)
            "if" -> tokenFactory.newToken(TokenType.KEYWORD_IF)
            "else" -> tokenFactory.newToken(TokenType.KEYWORD_ELSE)
            "true" -> tokenFactory.newToken(TokenType.BOOL, true)
            "false" -> tokenFactory.newToken(TokenType.BOOL, false)
            "fun" -> tokenFactory.newToken(TokenType.KEYWORD_FUN)
            "loop" -> tokenFactory.newToken(TokenType.KEYWORD_LOOP)
            "break" -> tokenFactory.newToken(TokenType.KEYWORD_BREAK)
            "continue" -> tokenFactory.newToken(TokenType.KEYWORD_CONTINUE)
            "return" -> tokenFactory.newToken(TokenType.KEYWORD_RETURN)
            "number" -> tokenFactory.newToken(TokenType.KEYWORD_NUMBER)
            "bool" -> tokenFactory.newToken(TokenType.KEYWORD_BOOL)
            "string" -> tokenFactory.newToken(TokenType.KEYWORD_STRING)
            "array" -> tokenFactory.newToken(TokenType.KEYWORD_ARRAY)
            "type" -> tokenFactory.newToken(TokenType.KEYWORD_TYPE)
            "trait" -> tokenFactory.newToken(TokenType.KEYWORD_TRAIT)
            "new" -> tokenFactory.newToken(TokenType.KEYWORD_NEW)
            "async" -> tokenFactory.newToken(TokenType.KEYWORD_ASYNC)
            else -> tokenFactory.newToken(TokenType.IDENTIFIER, name)
        }
    }
}