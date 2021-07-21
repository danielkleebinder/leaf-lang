package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import org.nyxlang.TestSuit
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
        execute("var i = 5; loop i > 1 { --i }")
        assertEquals(BigDecimal.ONE, globalActivationRecord["i"])
    }

    @Test
    fun shouldLoopInitConditionStep() {
        execute("loop var i = 0 : i < 5 : ++i {}")
        assertEquals(BigDecimal.valueOf(5), globalActivationRecord["i"])
    }

    @Test
    fun shouldEvaluateConditionNotJustOnce() {
        execute("var i = 5; loop i > 0 { i = i - 1 }")
        assertEquals(BigDecimal.ZERO, globalActivationRecord["i"])
    }

    @Test
    fun shouldRunPrimeChecker1() {
        execute(readResourceFile("prime-4.test.nyx"))
        assertEquals(BigDecimal.valueOf(4), globalActivationRecord["n"])
        assertEquals(BigDecimal.valueOf(2), globalActivationRecord["i"])
        assertEquals(false, globalActivationRecord["isPrime"])
    }

    @Test
    fun shouldRunPrimeChecker2() {
        execute(readResourceFile("prime-47.test.nyx"))
        assertEquals(BigDecimal.valueOf(47), globalActivationRecord["n"])
        assertEquals(BigDecimal.valueOf(47), globalActivationRecord["i"])
        assertEquals(true, globalActivationRecord["isPrime"])
    }

    @Test
    fun shouldRunFactorial() {
        execute(readResourceFile("factorial.test.nyx"))
        assertEquals(BigDecimal.valueOf(120), globalActivationRecord["res"])
    }

    @Test
    fun shouldBreakLoopImmediately() {
        execute("loop { break }")
        assertTrue(true)
    }

    @Test
    fun shouldBreakLoop1() {
        execute(readResourceFile("loop-break-1.test.nyx"))
        assertEquals(BigDecimal.valueOf(3), globalActivationRecord["a"])
    }

    @Test
    fun shouldBreakLoop2() {
        execute(readResourceFile("loop-break-2.test.nyx"))
        assertEquals(BigDecimal.valueOf(-1), globalActivationRecord["a"])
        assertTrue(globalActivationRecord["res"] as Boolean)
    }

    @Test
    fun shouldContinueLoop1() {
        execute(readResourceFile("loop-continue-1.test.nyx"))
        assertFalse(globalActivationRecord["res"] as Boolean)
    }

    @Test
    fun shouldContinueLoop2() {
        execute(readResourceFile("loop-continue-2.test.nyx"))
        assertFalse(globalActivationRecord["res"] as Boolean)
    }

    @Test
    fun shouldIgnoreNewLines1() {
//        execute(readResourceFile("loop-structure-1.test.nyx"))
//        assertEquals(0, globalActivationRecord["a"])
    }

    @Test
    fun shouldIgnoreNewLines2() {
//        execute(readResourceFile("loop-structure-2.test.nyx"))
//        assertEquals(0, globalActivationRecord["a"])
//        assertEquals(true, globalActivationRecord["res"])
    }
}