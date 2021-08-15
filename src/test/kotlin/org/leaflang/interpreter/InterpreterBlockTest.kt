package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import java.math.BigDecimal

class InterpreterBlockTest : TestSuit() {

    @Test
    fun shouldShadowVariables() {
        execute("var a = 1; { var a = 2; a = 10; }")
        assertEquals(BigDecimal.ONE, valueOf("a"))
    }

    @Test
    fun shouldReassignNonBlockVariables() {
        execute("var a = 1; { a = 17; }")
        assertEquals(BigDecimal.valueOf(17), valueOf("a"))
    }

    @Test
    fun shouldDeleteVariablesAfterBlock() {
        execute("var a = 1; { const c = 17; }")
        assertFalse(globalActivationRecord.has("c"))
        assertTrue(globalActivationRecord.has("a"))
    }

    @Test
    fun shouldBeUsedAsExpression() {
        execute("const a = { const a = 10; var c = a + 5; c }")
        assertTrue(globalActivationRecord.has("a"))
        assertEquals(BigDecimal.valueOf(15), valueOf("a"))
    }
}