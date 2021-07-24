package org.nyxlang.integration

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import java.math.BigDecimal

/**
 * Performs a lot of different integration tests based on real life examples.
 */
class IntegrationTest : TestSuit() {

    @Test
    fun shouldComputeFactorialRecursively() {
        execute(readResourceFile("integration/factorial.test.nyx"))
        assertEquals(BigDecimal.valueOf(3628800), valueOf("res"))
    }

    @Test
    fun shouldGreet() {
        execute(readResourceFile("integration/greeting.test.nyx"))
        assertEquals("Hello Hello Hello Daniel", valueOf("res"))
    }

    @Test
    fun shouldSplitAndJoinString() {
        val res = execute(readResourceFile("integration/string-utils.test.nyx"))
        assertArrayEquals(arrayOf("Daniel,Peter,John", listOf("Daniel", "Peter", "John")), (res as List<*>).toTypedArray())
    }
}