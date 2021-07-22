package org.nyxlang.lexer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.lexer.exception.LexerException
import org.nyxlang.lexer.token.NumberToken
import org.nyxlang.lexer.token.arithmetic.MinusToken
import java.math.BigDecimal

class LexerNumberTest : TestSuit() {

    @Test
    fun shouldTokenizeSingleInt() {
        val tokens = tokenize("5")
        assertEquals(1, tokens.size)
        assertSame(NumberToken::class.java, tokens[0].javaClass)
        assertEquals(BigDecimal.valueOf(5L), (tokens[0] as NumberToken).getValue())
    }

    @Test
    fun shouldTokenizeMultipleInts() {
        val tokens = tokenize("5 10 38194 0")
        assertEquals(4, tokens.size)
        assertSame(NumberToken::class.java, tokens[0].javaClass)
        assertEquals(BigDecimal.valueOf(5L), (tokens[0] as NumberToken).getValue())
        assertSame(NumberToken::class.java, tokens[1].javaClass)
        assertEquals(BigDecimal.valueOf(10L), (tokens[1] as NumberToken).getValue())
        assertSame(NumberToken::class.java, tokens[2].javaClass)
        assertEquals(BigDecimal.valueOf(38194L), (tokens[2] as NumberToken).getValue())
        assertSame(NumberToken::class.java, tokens[3].javaClass)
        assertEquals(BigDecimal.valueOf(0L), (tokens[3] as NumberToken).getValue())
    }

    @Test
    fun shouldTokenizeNegativeInt() {
        val tokens = tokenize("-5 10 -42")
        assertEquals(5, tokens.size)
        assertSame(MinusToken::class.java, tokens[0].javaClass)
        assertEquals(BigDecimal.valueOf(5L), (tokens[1] as NumberToken).getValue())
        assertEquals(BigDecimal.valueOf(10L), (tokens[2] as NumberToken).getValue())
        assertSame(MinusToken::class.java, tokens[3].javaClass)
        assertEquals(BigDecimal.valueOf(42L), (tokens[4] as NumberToken).getValue())
    }

    @Test
    fun shouldTokenizeFloat() {
        val tokens = tokenize("-3.1415")
        assertEquals(2, tokens.size)
        assertSame(MinusToken::class.java, tokens[0].javaClass)
        assertSame(NumberToken::class.java, tokens[1].javaClass)
        assertEquals(BigDecimal.valueOf(3.1415), (tokens[1] as NumberToken).getValue())
    }

    @Test
    fun shouldTokenizeNumberSeparator() {
        val tokens = tokenize("500_000_0")
        assertEquals(1, tokens.size)
        assertSame(NumberToken::class.java, tokens[0].javaClass)
        assertEquals(BigDecimal.valueOf(5_000_000), (tokens[0] as NumberToken).getValue())
    }

    @Test
    fun shouldErrorForMultipleDecimalPoints() {
        assertThrows(LexerException::class.java) { tokenize("3.14.15") }
        assertThrows(LexerException::class.java) { tokenize("3.14.1.5") }
        assertThrows(LexerException::class.java) { tokenize(".314.5") }
    }
}