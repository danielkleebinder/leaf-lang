package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import org.nyxlang.TestSuit
import java.math.BigDecimal
import java.time.Duration

@Timeout(1)
class InterpreterLoopTest : TestSuit() {
    @Test
    fun shouldSkipWithConditionFalse() {
        val result = execute("loop false { true }")
        Assertions.assertEquals(emptyList, result)
    }

    @Test
    fun shouldLoopEndlesslyWithoutCondition() {
        try {
            Assertions.assertTimeoutPreemptively<Any?>(Duration.ofMillis(10)) { execute("loop { }") }
            Assertions.fail<Any>()
        } catch (e: Error) {
            // We entered an infinite loop, everything is fine
            println(e)
        }
    }

    @Test
    fun shouldLoopConditionOnly() {
        execute("var i = 5; loop i > 1 { --i }")
        Assertions.assertEquals(BigDecimal.ONE, globalActivationRecord["i"])
    }

    @Test
    fun shouldLoopInitConditionStep() {
        execute("loop var i = 0 : i < 5 : ++i {}")
        Assertions.assertEquals(BigDecimal.valueOf(5), globalActivationRecord["i"])
    }

    @Test
    fun shouldEvaluateConditionNotJustOnce() {
        execute("var i = 5; loop i > 0 { i = i - 1 }")
        Assertions.assertEquals(BigDecimal.ZERO, globalActivationRecord["i"])
    }

    @Test
    fun shouldRunPrimeChecker1() {
        var program = readResourceFile("prime-4.test.nyx")
        execute(program)
        Assertions.assertEquals(BigDecimal.valueOf(4), globalActivationRecord["n"])
        Assertions.assertEquals(BigDecimal.valueOf(2), globalActivationRecord["i"])
        Assertions.assertEquals(false, globalActivationRecord["isPrime"])
    }

    @Test
    fun shouldRunPrimeChecker2() {
        var program = readResourceFile("prime-47.test.nyx")
        execute(program)
        Assertions.assertEquals(BigDecimal.valueOf(47), globalActivationRecord["n"])
        Assertions.assertEquals(BigDecimal.valueOf(47), globalActivationRecord["i"])
        Assertions.assertEquals(true, globalActivationRecord["isPrime"])
    }

    @Test
    fun shouldRunFactorial() {
        val program = readResourceFile("factorial.test.nyx")
        execute(program)
        Assertions.assertEquals(BigDecimal.valueOf(120), globalActivationRecord["res"])
    }
}