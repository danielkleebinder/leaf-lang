package org.leaflang.native

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.interpreter.exception.DynamicSemanticException
import java.math.BigDecimal

class MathModuleTest : TestSuit() {

    @Test
    fun shouldReturnRandomBool() {
        execute("const a = randomBool()")
        assertTrue(valueOf("a") == true || valueOf("a") == false)
    }

    @Test
    fun shouldReturnRandomNumber() {
        execute("const a = random()")
        assertTrue(BigDecimal.ZERO <= valueOf("a") as BigDecimal)
        assertTrue(BigDecimal.ONE >= valueOf("a") as BigDecimal)
    }

    @Test
    fun shouldReturnRandomIntegerNumber() {
        for (i in 1..1000) {
            assertTrue(execute("randomInt()") is BigDecimal)
        }
    }

    @Test
    fun shouldReturnRandomUnsignedNumber() {
        for (i in 1..1000) {
            assertTrue(BigDecimal.ZERO <= execute("randomUInt()") as BigDecimal)
        }
    }

    @Test
    fun shouldReturnSquareRoot() {
        assertEquals(BigDecimal.valueOf(0), execute("sqrt(0)"))
        assertEquals(BigDecimal.valueOf(3), execute("sqrt(9)"))
        assertEquals(BigDecimal.valueOf(5), execute("sqrt(25)"))
        assertEquals(BigDecimal.valueOf(127), execute("sqrt(16129)"))
    }

    @Test
    fun shouldReturnAbsolute() {
        assertEquals(BigDecimal.valueOf(0), execute("abs(0)"))
        assertEquals(BigDecimal.valueOf(74328), execute("abs(74328)"))
        assertEquals(BigDecimal.valueOf(71), execute("abs(-71)"))
    }

    @Test
    fun shouldReturnRounded() {
        assertEquals(BigDecimal.valueOf(0), execute("round(0)"))
        assertEquals(BigDecimal.valueOf(3), execute("round(3.141592654)"))
        assertEquals(BigDecimal.valueOf(-83), execute("round(-82.849381)"))
        assertEquals(BigDecimal.valueOf(-8422), execute("round(-8422.37493)"))
        assertArrayEquals(arrayOfBigDecimal(9, 8, -2), (execute("round(9.3, 7.5432, -2.2)") as List<*>).toTypedArray())
    }

    @Test
    fun shouldReturnMin() {
        assertEquals(BigDecimal.valueOf(1), execute("min(1)"))
        assertEquals(BigDecimal.valueOf(-15), execute("min(-15, 3, 7, 100, -10, 5)"))
        assertEquals(BigDecimal.valueOf(-10293), execute("min(99, 98, -10293, 97)"))
        assertEquals(BigDecimal.valueOf(96), execute("min(99, 98, 97, 96)"))
        assertEquals(BigDecimal.valueOf(0), execute("min(0, 0, 0)"))
    }

    @Test
    fun shouldReturnMax() {
        assertEquals(BigDecimal.valueOf(1), execute("max(1)"))
        assertEquals(BigDecimal.valueOf(100), execute("max(-15, 3, 7, 100, -10, 5)"))
        assertEquals(BigDecimal.valueOf(99), execute("max(99, 98, -10293, 97)"))
        assertEquals(BigDecimal.valueOf(-3), execute("max(-7, -105, -3, -60)"))
        assertEquals(BigDecimal.valueOf(0), execute("max(0, 0, 0)"))
    }

    @Test
    fun shouldErrorForZeroArguments() {
        assertThrows(DynamicSemanticException::class.java) { execute("sqrt()") }
        assertThrows(DynamicSemanticException::class.java) { execute("abs()") }
        assertThrows(DynamicSemanticException::class.java) { execute("round()") }
        assertThrows(DynamicSemanticException::class.java) { execute("min()") }
        assertThrows(DynamicSemanticException::class.java) { execute("max()") }
    }

    @Test
    fun shouldErrorForInvalidArgumentTypes() {
        assertThrows(DynamicSemanticException::class.java) { execute("sqrt(\"Test String\")") }
        assertThrows(DynamicSemanticException::class.java) { execute("abs(true)") }
        assertThrows(DynamicSemanticException::class.java) { execute("round(false)") }
        assertThrows(DynamicSemanticException::class.java) { execute("min(\"Hello World\")") }
        assertThrows(DynamicSemanticException::class.java) { execute("max(\"Another Test String\")") }
    }
}