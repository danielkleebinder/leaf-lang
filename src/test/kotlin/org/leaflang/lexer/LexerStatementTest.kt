package org.leaflang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.lexer.token.TokenType

class LexerStatementTest : TestSuit() {

    @Test
    fun shouldSeparateWithSemicolon() {
        val tokens = tokenize("true;true;false")
        assertEquals(6, tokens.size)
        assertSame(TokenType.SEPARATOR, tokens[1].kind)
        assertSame(TokenType.SEPARATOR, tokens[3].kind)
    }

    @Test
    fun shouldSeparateWithNewLine() {
        val tokens = tokenize("true\ntrue\nfalse")
        assertEquals(6, tokens.size)
        assertSame(TokenType.NEW_LINE, tokens[1].kind)
        assertSame(TokenType.NEW_LINE, tokens[3].kind)
    }
}