package org.leaflang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.lexer.token.TokenType

class LexerNumberTest : TestSuit() {

    @Test
    fun shouldTokenizeSingleInt() {
        val tokens = tokenize("5")
        assertEquals(2, tokens.size)
        assertEquals(TokenType.NUMBER, tokens[0].kind)
        assertEquals(5.0, tokens[0].value)
    }

    @Test
    fun shouldTokenizeMultipleInts() {
        val tokens = tokenize("5 10 38194 0")
        assertEquals(5, tokens.size)
        assertEquals(TokenType.NUMBER, tokens[0].kind)
        assertEquals(5.0, tokens[0].value)
        assertEquals(TokenType.NUMBER, tokens[1].kind)
        assertEquals(10.0, tokens[1].value)
        assertEquals(TokenType.NUMBER, tokens[2].kind)
        assertEquals(38194.0, tokens[2].value)
        assertEquals(TokenType.NUMBER, tokens[3].kind)
        assertEquals(0.0, tokens[3].value)
    }

    @Test
    fun shouldTokenizeNegativeInt() {
        val tokens = tokenize("-5 10 -42")
        assertEquals(6, tokens.size)
        assertEquals(TokenType.MINUS, tokens[0].kind)
        assertEquals(5.0, tokens[1].value)
        assertEquals(10.0, tokens[2].value)
        assertEquals(TokenType.MINUS, tokens[3].kind)
        assertEquals(42.0, tokens[4].value)
    }

    @Test
    fun shouldTokenizeFloat() {
        val tokens = tokenize("-3.1415")
        assertEquals(3, tokens.size)
        assertEquals(TokenType.MINUS, tokens[0].kind)
        assertEquals(TokenType.NUMBER, tokens[1].kind)
        assertEquals(3.1415, tokens[1].value)
    }

    @Test
    fun shouldTokenizeNumberSeparator() {
        val tokens = tokenize("500_000_0")
        assertEquals(2, tokens.size)
        assertEquals(TokenType.NUMBER, tokens[0].kind)
        assertEquals(5_000_000.0, tokens[0].value)
    }

    @Test
    fun shouldErrorForMultipleDecimalPoints() {
        assertEquals(TokenType.ERROR, tokenize("3.14.15")[0].kind)
        assertEquals(TokenType.ERROR, tokenize("3.14.1.5")[0].kind)
        assertEquals(TokenType.ERROR, tokenize(".314.5")[0].kind)
    }
}