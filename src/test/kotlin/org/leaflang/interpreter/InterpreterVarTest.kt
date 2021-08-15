package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.analyzer.exception.StaticSemanticException
import java.math.BigDecimal

class InterpreterVarTest : TestSuit() {

    @Test
    fun shouldDeclareConst() {
        execute("const a = 3.1415")
        assertEquals(BigDecimal.valueOf(3.1415), valueOf("a"))

        execute("const b = 42;")
        assertEquals(BigDecimal.valueOf(42), valueOf("b"))

        execute("const c = -993;")
        assertEquals(BigDecimal.valueOf(-993), valueOf("c"))
    }

    @Test
    fun shouldDeclareVar() {
        execute("var a = -37")
        assertEquals(BigDecimal.valueOf(-37), valueOf("a"))

        execute("var b = 42;")
        assertEquals(BigDecimal.valueOf(42), valueOf("b"))

        execute("var c: bool")
        assertNull(valueOf("c"))
    }

    @Test
    fun shouldDeclareTypedConst() {
        execute("const a: number = -37")
        assertEquals(BigDecimal.valueOf(-37), valueOf("a"))

        execute("const b: bool = true;")
        assertNotNull(valueOf("b"))
        assertTrue(valueOf("b") as Boolean)

        execute("var c: bool")
        assertNull(valueOf("c"))
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
        assertTrue(withErrors { execute("const a") } > 0)

        // Constants cannot be reassigned
        assertThrows(StaticSemanticException::class.java) { execute("const b = 3; b = 7;") }
    }

    @Test
    fun shouldErrorInvalidVarDeclaration() {

        // Uninitialized vars must have data type
        assertTrue(withErrors { execute("var a") } > 0)
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
        assertNotNull(valueOf("a"))
        assertNotNull(valueOf("b"))
        assertTrue(valueOf("b") as Boolean)

        execute("const c = 10.77; const d = c")
        assertNotNull(valueOf("c"))
        assertNotNull(valueOf("d"))
        assertEquals(BigDecimal.valueOf(10.77), valueOf("d"))
    }

    @Test
    fun shouldApplyUnary() {
        assertFalse(execute("const a = true; !a") as Boolean)
        assertFalse(execute("const b = true; ~b") as Boolean)
        assertEquals(BigDecimal.valueOf(3), execute("const c = [1,2,3]; ~c"))
        assertEquals(BigDecimal.valueOf(-4), execute("const d = 3; ~d"))
    }

    @Test
    fun shouldDeclareTypes() {
        assertDoesNotThrow { execute("const x1: array; var y1: array") }
        assertDoesNotThrow { execute("const x2: bool; var y2: bool") }
        assertDoesNotThrow { execute("const x3: fun; var y3: fun") }
        assertDoesNotThrow { execute("const x4: number; var y4: number") }
        assertDoesNotThrow { execute("const x5: string; var y5: string") }
    }
}