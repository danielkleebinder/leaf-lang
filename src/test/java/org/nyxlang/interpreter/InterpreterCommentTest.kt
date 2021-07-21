package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import java.math.BigDecimal

class InterpreterCommentTest : TestSuit() {
    @Test
    fun shouldSkipComment() {
        execute("// var a = 1")
        assertFalse(globalActivationRecord.has("a"))

        execute("//const b=1; var c=2")
        assertFalse(globalActivationRecord.has("b"))
    }

    @Test
    fun shouldSkipInlineComment() {
        execute("const a = 101; // var b = 1")
        assertTrue(globalActivationRecord.has("a"))
        assertFalse(globalActivationRecord.has("b"))
        assertEquals(BigDecimal.valueOf(101), globalActivationRecord["a"])

        execute("const x = 101 //, y = 1")
        assertTrue(globalActivationRecord.has("x"))
        assertFalse(globalActivationRecord.has("y"))
        assertEquals(BigDecimal.valueOf(101), globalActivationRecord["x"])
    }

    @Test
    fun shouldSkipMultipleInlineComments() {
        val programCode = """
            // var a = 1
            var b = 2
            // This is another test
            // var c = 3
            var d = 4
            """.trimIndent()
        execute(programCode)

        assertFalse(globalActivationRecord.has("a"))
        assertTrue(globalActivationRecord.has("b"))
        assertEquals(BigDecimal.valueOf(2), globalActivationRecord["b"])

        assertFalse(globalActivationRecord.has("c"))
        assertTrue(globalActivationRecord.has("d"))
        assertEquals(BigDecimal.valueOf(4), globalActivationRecord["d"])
    }
}