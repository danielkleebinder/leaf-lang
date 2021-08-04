package org.nyxlang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.lexer.token.TokenType

class LexerNameTest : TestSuit() {

    @Test
    fun shouldTokenizeLowerCase() {
        val tokens = tokenize("myvar")
        assertEquals(2, tokens.size)
        assertEquals(TokenType.IDENTIFIER, tokens[0].kind)
        assertEquals("myvar", tokens[0].value)
    }

    @Test
    fun shouldTokenizeUpperCase() {
        val tokens = tokenize("MYVAR")
        assertEquals(2, tokens.size)
        assertEquals(TokenType.IDENTIFIER, tokens[0].kind)
        assertEquals("MYVAR", tokens[0].value)
    }

    @Test
    fun shouldTokenizeMixedCase() {
        var tokens = tokenize("MyVar")
        assertEquals(2, tokens.size)
        assertEquals(TokenType.IDENTIFIER, tokens[0].kind)
        assertEquals("MyVar", tokens[0].value)

        tokens = tokenize("myVar")
        assertEquals(2, tokens.size)
        assertEquals(TokenType.IDENTIFIER, tokens[0].kind)
        assertEquals("myVar", tokens[0].value)
    }

    @Test
    fun shouldTokenizeWithNumbers() {
        var tokens = tokenize("myVar1")
        assertEquals(2, tokens.size)
        assertEquals(TokenType.IDENTIFIER, tokens[0].kind)
        assertEquals("myVar1", tokens[0].value)

        tokens = tokenize("my3var2")
        assertEquals(2, tokens.size)
        assertEquals(TokenType.IDENTIFIER, tokens[0].kind)
        assertEquals("my3var2", tokens[0].value)
    }

    @Test
    fun shouldTokenizeWithUnderline() {
        var tokens = tokenize("my_var_1")
        assertEquals(2, tokens.size)
        assertEquals(TokenType.IDENTIFIER, tokens[0].kind)
        assertEquals("my_var_1", tokens[0].value)

        tokens = tokenize("my_2var_1")
        assertEquals(2, tokens.size)
        assertEquals(TokenType.IDENTIFIER, tokens[0].kind)
        assertEquals("my_2var_1", tokens[0].value)
    }

    @Test
    fun shouldEnsureStartsWithLetter() {
        val tokens = tokenize("1myvar")
        assertNotSame(TokenType.IDENTIFIER, tokens[0].kind)
    }
}