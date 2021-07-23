package org.nyxlang.lexer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.lexer.token.NameToken

class LexerNameTest : TestSuit() {

    @Test
    fun shouldTokenizeLowerCase() {
        val tokens = tokenize("myvar")
        assertEquals(1, tokens.size)
        assertSame(NameToken::class.java, tokens[0].javaClass)
        assertEquals("myvar", (tokens[0] as NameToken).value)
    }

    @Test
    fun shouldTokenizeUpperCase() {
        val tokens = tokenize("MYVAR")
        assertEquals(1, tokens.size)
        assertSame(NameToken::class.java, tokens[0].javaClass)
        assertEquals("MYVAR", (tokens[0] as NameToken).value)
    }

    @Test
    fun shouldTokenizeMixedCase() {
        var tokens = tokenize("MyVar")
        assertEquals(1, tokens.size)
        assertSame(NameToken::class.java, tokens[0].javaClass)
        assertEquals("MyVar", (tokens[0] as NameToken).value)

        tokens = tokenize("myVar")
        assertEquals(1, tokens.size)
        assertSame(NameToken::class.java, tokens[0].javaClass)
        assertEquals("myVar", (tokens[0] as NameToken).value)
    }

    @Test
    fun shouldTokenizeWithNumbers() {
        var tokens = tokenize("myVar1")
        assertEquals(1, tokens.size)
        assertSame(NameToken::class.java, tokens[0].javaClass)
        assertEquals("myVar1", (tokens[0] as NameToken).value)

        tokens = tokenize("my3var2")
        assertEquals(1, tokens.size)
        assertSame(NameToken::class.java, tokens[0].javaClass)
        assertEquals("my3var2", (tokens[0] as NameToken).value)
    }

    @Test
    fun shouldTokenizeWithUnderline() {
        var tokens = tokenize("my_var_1")
        assertEquals(1, tokens.size)
        assertSame(NameToken::class.java, tokens[0].javaClass)
        assertEquals("my_var_1", (tokens[0] as NameToken).value)

        tokens = tokenize("my_2var_1")
        assertEquals(1, tokens.size)
        assertSame(NameToken::class.java, tokens[0].javaClass)
        assertEquals("my_2var_1", (tokens[0] as NameToken).value)
    }

    @Test
    fun shouldEnsureStartsWithLetter() {
        var tokens = tokenize("1myvar")
        assertNotSame(NameToken::class.java, tokens[0].javaClass)
    }
}