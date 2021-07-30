package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.nyxlang.TestSuit
import org.nyxlang.parser.exception.ParserException

class InterpreterCustomTypeTest : TestSuit() {

    @Test
    fun shouldDeclareSimpleType() {
        assertDoesNotThrow { execute("type User1") }
        assertDoesNotThrow { execute("type User2;type Item1") }
        assertDoesNotThrow { execute("type myCustomType1") }
        assertDoesNotThrow { execute("type User3; type Item2; type Currency") }
        assertDoesNotThrow {
            execute("""
            type User4
            
            type Item3
        """.trimIndent())
        }
    }

    @Test
    fun shouldDeclareFields() {
        assertDoesNotThrow { execute("type User { name: string }") }
    }

    @Test
    fun shouldUseType() {
        assertDoesNotThrow { execute("type User; const me: User") }
    }

    @Test
    fun shouldErrorIfNoTypeName() {
        assertThrows(ParserException::class.java) { execute("type") }
    }
}