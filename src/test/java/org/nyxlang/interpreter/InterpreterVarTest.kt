package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.analyzer.exception.StaticSemanticException
import org.nyxlang.parser.exception.ParserException
import java.math.BigDecimal

class InterpreterVarTest : TestSuit() {

    @Test
    fun shouldDeclareConst() {
        execute("const a = 3.1415")
        assertEquals(BigDecimal.valueOf(3.1415), globalMemory.get("a"))

        execute("const b = 42;")
        assertEquals(BigDecimal.valueOf(42), globalMemory.get("b"))

        execute("const c = -993;")
        assertEquals(BigDecimal.valueOf(-993), globalMemory.get("c"))
    }

    @Test
    fun shouldDeclareVar() {
        execute("var a = -37")
        assertEquals(BigDecimal.valueOf(-37), globalMemory.get("a"))

        execute("var b = 42;")
        assertEquals(BigDecimal.valueOf(42), globalMemory.get("b"))

        execute("var c: bool")
        assertNull(globalMemory.get("c"))
    }

    @Test
    fun shouldDeclareTypedConst() {
        execute("const a: number = -37")
        assertEquals(BigDecimal.valueOf(-37), globalMemory.get("a"))

        execute("const b: bool = true;")
        assertNotNull(globalMemory.get("b"))
        assertTrue(globalMemory.get("b") as Boolean)

        execute("var c: bool")
        assertNull(globalMemory.get("c"))
    }

    @Test
    fun shouldErrorInvalidConstAssignment() {

        // Constants must be initialized on declaration
        assertThrows(ParserException::class.java) { execute("const a") }

        // Constants cannot be reassigned
        assertThrows(StaticSemanticException::class.java) { execute("const b = 3; b = 7;") }
    }

    @Test
    fun shouldErrorInvalidVarDeclaration() {

        // Uninitialized vars must have data type
        assertThrows(ParserException::class.java) { execute("var a") }
    }

    @Test
    fun shouldErrorUnknownTypeDeclaration() {
        assertThrows(StaticSemanticException::class.java) { execute("var a: customUnknownType") }
    }

    @Test
    fun shouldErrorForRedeclaration() {
        assertThrows(StaticSemanticException::class.java) { execute("var a: number; var a: number;") }
        assertThrows(StaticSemanticException::class.java) { execute("const a = 7; var a: number;") }
        assertThrows(StaticSemanticException::class.java) { execute("const a: number; const a = 7;") }
        assertThrows(StaticSemanticException::class.java) { execute("const a: number, a: number, b: number;") }
    }
}