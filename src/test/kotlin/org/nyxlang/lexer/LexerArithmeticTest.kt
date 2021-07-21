package org.nyxlang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.lexer.token.NumberToken
import org.nyxlang.lexer.token.arithmetic.MultiplyToken
import org.nyxlang.lexer.token.arithmetic.PlusToken

class LexerArithmeticTest : TestSuit() {
    @Test
    fun shouldTokenizePlusOperator() {
        val tokens = tokenize("5+3")
        assertEquals(3, tokens.size)
        assertSame(NumberToken::class.java, tokens[0].javaClass)
        assertSame(PlusToken::class.java, tokens[1].javaClass)
        assertSame(NumberToken::class.java, tokens[2].javaClass)
    }

    @Test
    fun shouldTokenizeMultipleOperators() {
        val tokens = tokenize("5+3**10")
        assertEquals(6, tokens.size)
        assertSame(NumberToken::class.java, tokens[0].javaClass)
        assertSame(PlusToken::class.java, tokens[1].javaClass)
        assertSame(NumberToken::class.java, tokens[2].javaClass)
        assertSame(MultiplyToken::class.java, tokens[3].javaClass)
        assertSame(MultiplyToken::class.java, tokens[4].javaClass)
        assertSame(NumberToken::class.java, tokens[5].javaClass)
    }
}