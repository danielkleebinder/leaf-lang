package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.nyxlang.TestSuit
import org.nyxlang.parser.exception.ParserException

class InterpreterCustomTypeTest : TestSuit() {

    @Test
    fun shouldDeclareSimpleType() {
        assertDoesNotThrow { execute("type User") }
        assertDoesNotThrow { execute("type User;type Item") }
        assertDoesNotThrow { execute("type myCustomType1") }
        assertDoesNotThrow { execute("type User; type Item; type Currency") }
    }

    @Test
    fun shouldErrorIfNoTypeName() {
        assertThrows(ParserException::class.java) { execute("type") }
    }
}