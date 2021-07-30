package org.nyxlang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.keyword.TypeKeywordToken

class LexerCustomTypeTest : TestSuit() {

    @Test
    fun shouldTokenizeCustomTypeOnly() {
        val tokens = tokenize("type User")
        assertEquals(2, tokens.size)
        assertSame(TypeKeywordToken::class.java, tokens[0].javaClass)
        assertSame(NameToken::class.java, tokens[1].javaClass)
    }

    @Test
    fun shouldTokenizeCustomTypes() {
        val tokens = tokenize("type User; type Item; type Currency")
        assertEquals(8, tokens.size)
        assertSame(TypeKeywordToken::class.java, tokens[0].javaClass)
        assertSame(NameToken::class.java, tokens[1].javaClass)
        assertSame(TypeKeywordToken::class.java, tokens[3].javaClass)
        assertSame(NameToken::class.java, tokens[4].javaClass)
        assertSame(TypeKeywordToken::class.java, tokens[6].javaClass)
        assertSame(NameToken::class.java, tokens[7].javaClass)
    }
}