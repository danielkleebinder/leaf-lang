package org.leaflang.integration

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import org.leaflang.TestSuit
import java.math.BigDecimal
import java.time.Duration

/**
 * Performs performance load tests on the programming language.
 */
@Timeout(2)
class PerformanceTest : TestSuit() {

    @Test
    fun shouldComputeFactorialRecursively() {
        Assertions.assertTimeoutPreemptively<Any?>(Duration.ofSeconds(2)) {
            execute(readResourceFile("integration/fibonacci.test.leaf"))
        }
        assertEquals(BigDecimal.valueOf(317_811), valueOf("res"))
    }

    @Test
    fun shouldConcatenateStrings() {
        Assertions.assertTimeoutPreemptively<Any?>(Duration.ofSeconds(2)) {
            execute(readResourceFile("integration/string-concat.test.leaf"))
        }
    }
}