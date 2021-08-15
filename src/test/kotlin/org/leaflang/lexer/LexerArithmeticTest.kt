package org.leaflang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.lexer.token.TokenType

class LexerArithmeticTest : TestSuit() {

    @Test
    fun shouldTokenizePlusOperator() {
        val tokens = tokenize("5+3")
        assertEquals(4, tokens.size)
        assertEquals(TokenType.NUMBER, tokens[0].kind)
        assertEquals(TokenType.PLUS, tokens[1].kind)
        assertEquals(TokenType.NUMBER, tokens[2].kind)
        assertEquals(TokenType.END_OF_PROGRAM, tokens[3].kind)
    }

    @Test
    fun shouldTokenizeMultipleOperators() {
        val tokens = tokenize("5+3**10")
        assertEquals(7, tokens.size)
        assertEquals(TokenType.NUMBER, tokens[0].kind)
        assertEquals(TokenType.PLUS, tokens[1].kind)
        assertEquals(TokenType.NUMBER, tokens[2].kind)
        assertEquals(TokenType.TIMES, tokens[3].kind)
        assertEquals(TokenType.TIMES, tokens[4].kind)
        assertEquals(TokenType.NUMBER, tokens[5].kind)
        assertEquals(TokenType.END_OF_PROGRAM, tokens[6].kind)
    }
}