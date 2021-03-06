package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import java.math.BigDecimal

class InterpreterIfTest : TestSuit() {

    @Test
    fun shouldInterpretSimpleConditional() {
        assertTrue(execute("if true { true }") as Boolean)
        assertNull(execute("if false { true }"))
    }

    @Test
    fun shouldInterpretEmptyBody() {
        assertNull(execute("if true {}"))
    }

    @Test
    fun shouldInterpretEmptyCondition() {
        assertNull(execute("if { true }"))
    }

    @Test
    fun shouldInterpretConditionExpression() {
        var result = execute("if (1 == -4) { true }")
        assertNull(result)

        result = execute("if !(1 == 2) && (~3 == -4) { true }")
        assertTrue(result as Boolean)

        result = execute("if (1 == 2) && (~3 == -4) { true }")
        assertNull(result)
    }

    @Test
    fun shouldInterpretBodyExpression() {
        var result = execute("if true { 1 + 1 }")
        assertEquals(BigDecimal.valueOf(2), result)

        result = execute("if true { (1 == 2) && (~3 == -4) }")
        assertFalse(result as Boolean)
    }

    @Test
    fun shouldInterpretIfElse() {
        var result = execute("if false { 1 } else { 2 }")
        assertEquals(BigDecimal.valueOf(2), result)
        result = execute("if ((1 == 2) && (~3 == -4)) { 1 } else { 2 }")
        assertEquals(BigDecimal.valueOf(2), result)
    }

    @Test
    fun shouldInterpretElseIf() {
        var result = execute("if 1 == 0 { 0 } else if 1 == 1 { 1 } else { 2 }")
        assertEquals(BigDecimal.valueOf(1), result)

        result = execute("if 1 == 0 { 0 } else if 1 == 10*10 { 1 } else { 2 }")
        assertEquals(BigDecimal.valueOf(2), result)
    }

    @Test
    fun shouldIgnoreNewLines1() {
        execute(readSourceFile("conditional-1.test.leaf"))
        assertTrue(valueOf("res") as Boolean)
    }

    @Test
    fun shouldIgnoreNewLines2() {
        execute(readSourceFile("conditional-2.test.leaf"))
        assertTrue(valueOf("res") as Boolean)
    }

    @Test
    fun shouldBeUsedAsExpression() {
        execute("const a = if true { 10 } else { 15 }")
        assertTrue(globalActivationRecord.has("a"))
        assertEquals(BigDecimal.valueOf(10), valueOf("a"))

        execute("const x = if 1 + 1 == 0 { 10 } else { 15 }")
        assertTrue(globalActivationRecord.has("x"))
        assertEquals(BigDecimal.valueOf(15), valueOf("x"))
    }

    @Test
    fun shouldAllowNestedIfExpressions() {
        execute("const res = if if if if true { true } else { false } { true } { 1 == 1 } { 10 }")
        assertTrue(globalActivationRecord.has("res"))
        assertEquals(BigDecimal.valueOf(10), valueOf("res"))
    }
}