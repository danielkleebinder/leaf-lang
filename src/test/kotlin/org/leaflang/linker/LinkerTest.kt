package org.leaflang.linker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import java.math.BigDecimal

class LinkerTest : TestSuit() {

    @Test
    fun shouldLinkTwoFiles() {
        execute(readSourceFile("use/linker-1-1.test.leaf"))
        assertEquals(BigDecimal.valueOf(10), valueOf("res"))
    }

    @Test
    fun shouldLinkRecursively() {
        execute(readSourceFile("use/linker-2-1.test.leaf"))
        assertEquals(BigDecimal.valueOf(442), valueOf("res"))
    }

    @Test
    fun shouldLinkMultipleFiles() {
        execute(readSourceFile("use/linker-3-1.test.leaf"))
        assertEquals(BigDecimal.valueOf(25), valueOf("res"))
    }

    @Test
    fun shouldLinkSameFileOnlyOnce() {
        execute(readSourceFile("use/linker-4-1.test.leaf"))
        assertEquals(BigDecimal.valueOf(55), valueOf("res"))
    }
}