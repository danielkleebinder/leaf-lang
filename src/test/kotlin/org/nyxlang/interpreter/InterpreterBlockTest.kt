package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import java.math.BigDecimal

class InterpreterBlockTest : TestSuit() {

    @Test
    fun shouldShadowVariables() {
        execute("var a = 1; { var a = 2; a = 10; }")
        assertEquals(BigDecimal.ONE, globalActivationRecord["a"])
    }

    @Test
    fun shouldReassignNonBlockVariables() {
        execute("var a = 1; { a = 17; }")
        assertEquals(BigDecimal.valueOf(17), globalActivationRecord["a"])
    }

    @Test
    fun shouldDeleteVariablesAfterBlock() {
        execute("var a = 1; { const c = 17; }")
        assertFalse(globalActivationRecord.has("c"))
        assertTrue(globalActivationRecord.has("a"))
    }
}