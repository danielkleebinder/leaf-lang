package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.interpreter.exception.DynamicSemanticException
import java.math.BigDecimal

class InterpreterArithmeticTest : TestSuit() {
    @Test
    fun shouldBuildSum() {
        var result = execute("3 + 1 + 80")
        assertEquals(BigDecimal.valueOf(84), result)

        result = execute("(100 + 2) + 7 + 1")
        assertEquals(BigDecimal.valueOf(110), result)
    }

    @Test
    fun shouldBuildDifference() {
        var result = execute("10-7")
        assertEquals(BigDecimal.valueOf(3), result)

        result = execute("(100 - 80) + 10")
        assertEquals(BigDecimal.valueOf(30), result)

        result = execute("100 - (80 + 10)")
        assertEquals(BigDecimal.valueOf(10), result)
    }

    @Test
    fun shouldMultiply() {
        var result = execute("10*2*5")
        assertEquals(BigDecimal.valueOf(100), result)

        result = execute("10*-2*5")
        assertEquals(BigDecimal.valueOf(-100), result)
    }

    @Test
    fun shouldDivide() {
        val result = execute("12/3/2")
        assertEquals(BigDecimal.valueOf(2), result)
    }

    @Test
    fun shouldModulo() {
        var result = execute("4 % 2")
        assertEquals(BigDecimal.valueOf(0), result)

        result = execute("3 % 2")
        assertEquals(BigDecimal.valueOf(1), result)

        result = execute("2 % 3")
        assertEquals(BigDecimal.valueOf(2), result)

        result = execute("10 % 10")
        assertEquals(BigDecimal.valueOf(0), result)
    }

    @Test
    fun shouldPower() {
        var result = execute("3**3")
        assertEquals(BigDecimal.valueOf(27.0), result)

        result = execute("27**0")
        assertEquals(BigDecimal.valueOf(1.0), result)

        result = execute("27**2")
        assertEquals(BigDecimal.valueOf(729.0), result)
    }

    @Test
    fun shouldUseCorrectPrecedence1() {
        val result = execute("7 + 3 * (10 / (12 / (3 + 1) - 1))")
        assertEquals(BigDecimal.valueOf(22), result)
    }

    @Test
    fun shouldUseCorrectPrecedence2() {
        val result = execute("7 + 3 * (10 / (12 / (3 + 1) - 1)) / (2 + 3) - 5 - 3 + (8)")
        assertEquals(BigDecimal.valueOf(10), result)
    }

    @Test
    fun shouldUseCorrectPrecedence3() {
        val result = execute("7 + (((3 + 2)))")
        assertEquals(BigDecimal.valueOf(12), result)
    }

    @Test
    fun shouldUseCorrectPrecedence4() {
        var result = execute("10 - 8 / 2 < 10")
        assertTrue(result as Boolean)

        result = execute("10 > 10 - 8 / 2")
        assertTrue(result as Boolean)
    }

    @Test
    fun shouldAllowNegativeNumbers() {
        val result = execute("- 3")
        assertEquals(BigDecimal.valueOf(-3), result)
    }

    @Test
    fun shouldAllowNegativeNumberCalculation1() {
        val result = execute("5 - - - + - 3")
        assertEquals(BigDecimal.valueOf(8), result)
    }

    @Test
    fun shouldAllowNegativeNumberCalculation2() {
        val result = execute("5 - - - + - (3 + 4) - +2")
        assertEquals(BigDecimal.valueOf(10), result)
    }

    @Test
    fun shouldErrorForInvalidArithmetic() {
        assertThrows(DynamicSemanticException::class.java) { execute("2 && 1") }
        assertThrows(DynamicSemanticException::class.java) { execute("*1") }
    }

    @Test
    fun shouldHaveCorrectPrecedenceForEquals() {
        var result = execute("1 == 1")
        assertTrue(result as Boolean)

        result = execute("1 - 1 == 0")
        assertTrue(result as Boolean)

        result = execute("0 == 1 - 1")
        assertTrue(result as Boolean)

        result = execute("5 == 2 * 2 + 1")
        assertTrue(result as Boolean)
    }
}