package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import org.leaflang.TestSuit
import java.math.BigDecimal
import java.time.Duration

@Timeout(1)
class InterpreterLoopTest : TestSuit() {
    @Test
    fun shouldSkipWithConditionFalse() {
        assertNull(execute("loop false { true }"))
    }

    @Test
    fun shouldLoopEndlesslyWithoutCondition() {
        try {
            assertTimeoutPreemptively<Any?>(Duration.ofMillis(10)) { execute("loop { }") }
            fail()
        } catch (e: Error) {
            // We entered an infinite loop, everything is fine
            println(e)
        }
    }

    @Test
    fun shouldLoopConditionOnly() {
        execute("var i = 5; loop i > 1 { i = i - 1 }")
        assertEquals(BigDecimal.ONE, valueOf("i"))
    }

    @Test
    fun shouldLoopInitConditionStep() {
        execute("var i = 0; loop i = 0 : i < 5 : i = i + 1 {}")
        assertEquals(BigDecimal.valueOf(5), valueOf("i"))
    }

    @Test
    fun shouldEvaluateConditionNotJustOnce() {
        execute("var i = 5; loop i > 0 { i = i - 1 }")
        assertEquals(BigDecimal.ZERO, valueOf("i"))
    }

    @Test
    fun shouldAllowRedeclareOfSameInitVar() {
        assertDoesNotThrow {
            execute("""
                loop var i = 0 : i < 5 : i = i + 1 {}
                loop var i = 0 : i < 5 : i = i + 1 {}
            """.trimIndent())
        }
    }

    @Test
    fun shouldRunPrimeChecker1() {
        execute(readSourceFile("prime-4.test.leaf"))
        assertEquals(BigDecimal.valueOf(4), valueOf("n"))
        assertEquals(BigDecimal.valueOf(2), valueOf("i"))
        assertEquals(false, valueOf("isPrime"))
    }

    @Test
    fun shouldRunPrimeChecker2() {
        execute(readSourceFile("prime-47.test.leaf"))
        assertEquals(BigDecimal.valueOf(47), valueOf("n"))
        assertEquals(BigDecimal.valueOf(47), valueOf("i"))
        assertEquals(true, valueOf("isPrime"))
    }

    @Test
    fun shouldRunFactorial() {
        execute(readSourceFile("factorial.test.leaf"))
        assertEquals(BigDecimal.valueOf(120), valueOf("res"))
    }

    @Test
    fun shouldBreakLoopImmediately() {
        execute("loop { break }")
        assertTrue(true)
    }

    @Test
    fun shouldBreakLoop1() {
        execute(readSourceFile("loop-break-1.test.leaf"))
        assertEquals(BigDecimal.valueOf(3), valueOf("a"))
    }

    @Test
    fun shouldBreakLoop2() {
        execute(readSourceFile("loop-break-2.test.leaf"))
        assertEquals(BigDecimal.valueOf(-1), valueOf("a"))
        assertTrue(valueOf("res") as Boolean)
    }

    @Test
    fun shouldContinueLoop1() {
        execute(readSourceFile("loop-continue-1.test.leaf"))
        assertFalse(valueOf("res") as Boolean)
    }

    @Test
    fun shouldContinueLoop2() {
        execute(readSourceFile("loop-continue-2.test.leaf"))
        assertFalse(valueOf("res") as Boolean)
    }

    @Test
    fun shouldIgnoreNewLines1() {
        execute(readSourceFile("loop-structure-1.test.leaf"))
        assertEquals(BigDecimal.valueOf(0), valueOf("a"))
    }

    @Test
    fun shouldIgnoreNewLines2() {
        execute(readSourceFile("loop-structure-2.test.leaf"))
        assertEquals(BigDecimal.valueOf(0), valueOf("a"))
        assertEquals(true, valueOf("res"))
    }
}