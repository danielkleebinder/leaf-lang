package org.nyxlang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.lexer.token.AssignToken
import org.nyxlang.lexer.token.ColonToken
import org.nyxlang.lexer.token.NameToken
import org.nyxlang.lexer.token.NumberToken
import org.nyxlang.lexer.token.keyword.ConstKeywordToken
import org.nyxlang.lexer.token.keyword.NumberKeywordToken
import org.nyxlang.lexer.token.keyword.VarKeywordToken

class LexerTypeTest : TestSuit() {

    @Test
    fun shouldTokenizeTypeOnly() {
        val tokens = lexer.tokenize("var a: number")
        assertEquals(4, tokens.size)
        assertSame(VarKeywordToken::class.java, tokens[0].javaClass)
        assertSame(NameToken::class.java, tokens[1].javaClass)
        assertSame(ColonToken::class.java, tokens[2].javaClass)
        assertSame(NumberKeywordToken::class.java, tokens[3].javaClass)
    }

    @Test
    fun shouldTokenizeAssignmentOnly() {
        val tokens = lexer.tokenize("var a = 3")
        assertEquals(4, tokens.size)
        assertSame(VarKeywordToken::class.java, tokens[0].javaClass)
        assertSame(NameToken::class.java, tokens[1].javaClass)
        assertSame(AssignToken::class.java, tokens[2].javaClass)
        assertSame(NumberToken::class.java, tokens[3].javaClass)
    }

    @Test
    fun shouldTokenizeTypedAssignment() {
        val tokens = lexer.tokenize("var a: number = 3")
        assertEquals(6, tokens.size)
        assertSame(VarKeywordToken::class.java, tokens[0].javaClass)
        assertSame(NameToken::class.java, tokens[1].javaClass)
        assertSame(ColonToken::class.java, tokens[2].javaClass)
        assertSame(NumberKeywordToken::class.java, tokens[3].javaClass)
        assertSame(AssignToken::class.java, tokens[4].javaClass)
        assertSame(NumberToken::class.java, tokens[5].javaClass)
    }

    @Test
    fun shouldTokenizeTypedConstAssignment() {
        val tokens = lexer.tokenize("const a: number = 3")
        assertEquals(6, tokens.size)
        assertSame(ConstKeywordToken::class.java, tokens[0].javaClass)
        assertSame(NameToken::class.java, tokens[1].javaClass)
        assertSame(ColonToken::class.java, tokens[2].javaClass)
        assertSame(NumberKeywordToken::class.java, tokens[3].javaClass)
        assertSame(AssignToken::class.java, tokens[4].javaClass)
        assertSame(NumberToken::class.java, tokens[5].javaClass)
    }
}