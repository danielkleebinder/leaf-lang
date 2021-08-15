package org.leaflang.lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.lexer.token.TokenType

class LexerTypeTest : TestSuit() {

    @Test
    fun shouldTokenizeTypeOnly() {
        val tokens = tokenize("var a: number")
        assertEquals(5, tokens.size)
        assertEquals(TokenType.KEYWORD_VAR, tokens[0].kind)
        assertEquals(TokenType.IDENTIFIER, tokens[1].kind)
        assertEquals(TokenType.COLON, tokens[2].kind)
        assertEquals(TokenType.KEYWORD_NUMBER, tokens[3].kind)
    }

    @Test
    fun shouldTokenizeAssignmentOnly() {
        val tokens = tokenize("var a = 3")
        assertEquals(5, tokens.size)
        assertEquals(TokenType.KEYWORD_VAR, tokens[0].kind)
        assertEquals(TokenType.IDENTIFIER, tokens[1].kind)
        assertEquals(TokenType.ASSIGNMENT, tokens[2].kind)
        assertEquals(TokenType.NUMBER, tokens[3].kind)
    }

    @Test
    fun shouldTokenizeTypedAssignment() {
        val tokens = tokenize("var a: number = 3")
        assertEquals(7, tokens.size)
        assertEquals(TokenType.KEYWORD_VAR, tokens[0].kind)
        assertEquals(TokenType.IDENTIFIER, tokens[1].kind)
        assertEquals(TokenType.COLON, tokens[2].kind)
        assertEquals(TokenType.KEYWORD_NUMBER, tokens[3].kind)
        assertEquals(TokenType.ASSIGNMENT, tokens[4].kind)
        assertEquals(TokenType.NUMBER, tokens[5].kind)
    }

    @Test
    fun shouldTokenizeTypedConstAssignment() {
        val tokens = tokenize("const a: number = 3")
        assertEquals(7, tokens.size)
        assertEquals(TokenType.KEYWORD_CONST, tokens[0].kind)
        assertEquals(TokenType.IDENTIFIER, tokens[1].kind)
        assertEquals(TokenType.COLON, tokens[2].kind)
        assertEquals(TokenType.KEYWORD_NUMBER, tokens[3].kind)
        assertEquals(TokenType.ASSIGNMENT, tokens[4].kind)
        assertEquals(TokenType.NUMBER, tokens[5].kind)
    }
}