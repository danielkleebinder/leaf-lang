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
        assertEquals(BigDecimal.valueOf(3.1415), globalActivationRecord["a"])

        execute("const b = 42;")
        assertEquals(BigDecimal.valueOf(42), globalActivationRecord["b"])

        execute("const c = -993;")
        assertEquals(BigDecimal.valueOf(-993), globalActivationRecord["c"])
    }

    @Test
    fun shouldDeclareVar() {
        execute("var a = -37")
        assertEquals(BigDecimal.valueOf(-37), globalActivationRecord["a"])

        execute("var b = 42;")
        assertEquals(BigDecimal.valueOf(42), globalActivationRecord["b"])

        execute("var c: bool")
        assertNull(globalActivationRecord["c"])
    }

    @Test
    fun shouldDeclareTypedConst() {
        execute("const a: number = -37")
        assertEquals(BigDecimal.valueOf(-37), globalActivationRecord["a"])

        execute("const b: bool = true;")
        assertNotNull(globalActivationRecord["b"])
        assertTrue(globalActivationRecord["b"] as Boolean)

        execute("var c: bool")
        assertNull(globalActivationRecord["c"])
    }

    @Test
    fun shouldErrorUnknownVariableAccess() {
        assertThrows(StaticSemanticException::class.java) { execute("hello;") }
        assertThrows(StaticSemanticException::class.java) { execute("x;") }
        assertThrows(StaticSemanticException::class.java) { execute("Test;") }
        assertThrows(StaticSemanticException::class.java) { execute("z = true;") }
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
        assertThrows(StaticSemanticException::class.java) { execute("var a: numberType") }
        assertThrows(StaticSemanticException::class.java) { execute("var a: StringType") }
        assertThrows(StaticSemanticException::class.java) { execute("var a: StringT") }
    }

    @Test
    fun shouldErrorForRedeclaration() {
        assertThrows(StaticSemanticException::class.java) { execute("var a: number; var a: number;") }
        assertThrows(StaticSemanticException::class.java) { execute("const a = 7; var a: number;") }
        assertThrows(StaticSemanticException::class.java) { execute("const a: number; const a = 7;") }
        assertThrows(StaticSemanticException::class.java) { execute("const a: number, a: number, b: number;") }
    }

    @Test
    fun shouldAssignVariableOtherVariableValue() {
        execute("const a: bool = true; const b = a")
        assertNotNull(globalActivationRecord["a"])
        assertNotNull(globalActivationRecord["b"])
        assertTrue(globalActivationRecord["b"] as Boolean)

        execute("const c = 10.77; const d = c")
        assertNotNull(globalActivationRecord["c"])
        assertNotNull(globalActivationRecord["d"])
        assertEquals(BigDecimal.valueOf(10.77), globalActivationRecord["d"])
    }
}