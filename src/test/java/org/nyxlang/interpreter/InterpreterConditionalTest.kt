package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import java.math.BigDecimal
import java.util.*

class InterpreterConditionalTest : TestSuit() {

    @Test
    fun shouldInterpretSimpleConditional() {
        var result = execute("if true { true }")
        assertEquals(trueList, result)
        result = execute("if false { true }")
        assertEquals(emptyList, result)
    }

    @Test
    fun shouldInterpretEmptyBody() {
        val result = execute("if true {}")
        assertEquals(emptyList, result)
    }

    @Test
    fun shouldInterpretEmptyCondition() {
        val result = execute("if { true }")
        assertEquals(emptyList, result)
    }

    @Test
    fun shouldInterpretConditionExpression() {
        var result = execute("if (1 == -4) { true }")
        assertEquals(emptyList, result)
        result = execute("if !(1 == 2) && (~3 == -4) { true }")
        assertEquals(trueList, result)
        result = execute("if (1 == 2) && (~3 == -4) { true }")
        assertEquals(emptyList, result)
    }

    @Test
    fun shouldInterpretBodyExpression() {
        var result = execute("if true { 1 + 1 }")
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result)
        result = execute("if true { (1 == 2) && (~3 == -4) }")
        assertEquals(falseList, result)
    }

    @Test
    fun shouldInterpretIfElse() {
        var result = execute("if false { 1 } else { 2 }")
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result)
        result = execute("if ((1 == 2) && (~3 == -4)) { 1 } else { 2 }")
        assertEquals(Arrays.asList(BigDecimal.valueOf(2)), result)
    }

    @Test
    fun shouldInterpretElseIf() {
        var result = execute("if 1 == 0 { 0 } else if 1 == 1 { 1 } else { 2 }")
        assertEquals(listOf(BigDecimal.valueOf(1)), result)
        result = execute("if 1 == 0 { 0 } else if 1 == 10**10 { 1 } else { 2 }")
        assertEquals(listOf(BigDecimal.valueOf(2)), result)
    }

    @Test
    fun shouldIgnoreNewLines1() {
//        execute(readResourceFile("conditional-1.test.nyx"))
//        assertTrue(globalActivationRecord["res"] as Boolean)
    }

    @Test
    fun shouldIgnoreNewLines2() {
//        execute(readResourceFile("conditional-2.test.nyx"))
//        assertTrue(globalActivationRecord["res"] as Boolean)
    }
}