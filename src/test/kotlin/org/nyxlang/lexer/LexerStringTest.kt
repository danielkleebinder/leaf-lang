package org.nyxlang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.lexer.token.StringToken

class LexerStringTest : TestSuit() {

    @Test
    fun shouldTokenizeSimpleString() {
        val tokens = tokenize("\"Hi\"")
        assertEquals(1, tokens.size)
        assertSame(StringToken::class.java, tokens[0].javaClass)
        assertEquals("Hi", (tokens[0] as StringToken).value)
    }

    @Test
    fun shouldTokenizeMoreComplexString() {
        val string = "Hi, my name is Daniel and I am the developer of this cool programming language"
        val tokens = tokenize("\"$string\"")
        assertEquals(1, tokens.size)
        assertSame(StringToken::class.java, tokens[0].javaClass)
        assertEquals(string, (tokens[0] as StringToken).value)
    }

    @Test
    fun shouldTokenizeStringWithKeywords() {
        val string = "loop { if when { true others; :: val t = 2} }"
        val tokens = tokenize("\"$string\"")
        assertEquals(1, tokens.size)
        assertSame(StringToken::class.java, tokens[0].javaClass)
        assertEquals(string, (tokens[0] as StringToken).value)
    }

    @Test
    fun shouldTokenizeMultilineStrings() {
        val string = """Hello you,
            |this is a multiline string and I hope
            |my programming language understands this.
            |Best regards,
            |Daniel
        """.trimMargin()
        val tokens = tokenize("\"$string\"")
        assertEquals(1, tokens.size)
        assertSame(StringToken::class.java, tokens[0].javaClass)
        assertEquals(string, (tokens[0] as StringToken).value)
    }

    @Test
    fun shouldTokenizeStringWithEscapes() {
        val string = "Hello \\\"User\\\", my name is \\\\uke.\\nHere we see a new line!"
        val tokens = tokenize("\"$string\"")
        assertEquals(1, tokens.size)
        assertSame(StringToken::class.java, tokens[0].javaClass)
        assertEquals("Hello \"User\", my name is \\uke.\nHere we see a new line!", (tokens[0] as StringToken).value)
    }
}