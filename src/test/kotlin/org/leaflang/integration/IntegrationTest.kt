package org.leaflang.integration

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.interpreter.memory.cell.IMemoryCell
import java.math.BigDecimal

/**
 * Performs a lot of different integration tests based on real life examples.
 */
class IntegrationTest : TestSuit() {

    @Test
    fun shouldComputeFactorialRecursively() {
        execute(readResourceFile("integration/factorial.test.leaf"))
        assertEquals(BigDecimal.valueOf(3628800), valueOf("res"))
    }

    @Test
    fun shouldGreet() {
        execute(readResourceFile("integration/greeting.test.leaf"))
        assertEquals("Hello Hello Hello Daniel", valueOf("res"))
    }

    @Test
    fun shouldSplitAndJoinString() {
        execute(readResourceFile("integration/string-utils.test.leaf"))

        val result = valueOf("res") as Array<IMemoryCell>
        assertEquals(2, result.size)
        assertEquals("Daniel,Peter,John", result[0].value as String)
        assertArrayEquals(arrayOf("Daniel", "Peter", "John"), (result[1].value as Array<IMemoryCell>).map { it.value }.toTypedArray())
    }
}