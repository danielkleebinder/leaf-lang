package org.leaflang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.lexer.token.TokenType

class LexerCustomTypeTest : TestSuit() {

    @Test
    fun shouldTokenizeCustomTypeOnly() {
        val tokens = tokenize("type User")
        assertEquals(3, tokens.size)
        assertEquals(TokenType.KEYWORD_TYPE, tokens[0].kind)
        assertEquals(TokenType.IDENTIFIER, tokens[1].kind)
        assertEquals(TokenType.END_OF_PROGRAM, tokens[2].kind)
    }

    @Test
    fun shouldTokenizeCustomTypes() {
        val tokens = tokenize("type User; type Item; type Currency")
        assertEquals(9, tokens.size)
        assertEquals(TokenType.KEYWORD_TYPE, tokens[0].kind)
        assertEquals(TokenType.IDENTIFIER, tokens[1].kind)
        assertEquals(TokenType.KEYWORD_TYPE, tokens[3].kind)
        assertEquals(TokenType.IDENTIFIER, tokens[4].kind)
        assertEquals(TokenType.KEYWORD_TYPE, tokens[6].kind)
        assertEquals(TokenType.IDENTIFIER, tokens[7].kind)
        assertEquals(TokenType.END_OF_PROGRAM, tokens[8].kind)
    }
}