package org.nyxlang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.lexer.token.NewLineToken
import org.nyxlang.lexer.token.StatementSeparatorToken

class LexerStatementTest : TestSuit() {
    @Test
    fun shouldSeparateWithSemicolon() {
        val tokens = lexer.tokenize("true;true;false")
        assertEquals(5, tokens.size)
        assertSame(StatementSeparatorToken::class.java, tokens[1].javaClass)
        assertSame(StatementSeparatorToken::class.java, tokens[3].javaClass)
    }

    @Test
    fun shouldSeparateWithNewLine() {
        val tokens = lexer.tokenize("true\ntrue\nfalse")
        assertEquals(5, tokens.size)
        assertSame(NewLineToken::class.java, tokens[1].javaClass)
        assertSame(NewLineToken::class.java, tokens[3].javaClass)
    }
}