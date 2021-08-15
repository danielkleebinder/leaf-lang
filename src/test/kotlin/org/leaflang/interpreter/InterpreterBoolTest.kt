package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.interpreter.exception.DynamicSemanticException

class InterpreterBoolTest : TestSuit() {
    @Test
    fun shouldCompareTrueAndFalseKeywords() {
        var result = execute("true == false")
        assertFalse(result as Boolean)

        result = execute("true == true")
        assertTrue(result as Boolean)

        result = execute("true && true")
        assertTrue(result as Boolean)

        result = execute("false && true")
        assertFalse(result as Boolean)

        result = execute("false || true")
        assertTrue(result as Boolean)

        result = execute("false || (1 == 2)")
        assertFalse(result as Boolean)
    }

    @Test
    fun shouldComparePositiveNumbers() {
        var result = execute("1<2")
        assertTrue(result as Boolean)

        result = execute("1>2")
        assertFalse(result as Boolean)
    }

    @Test
    fun shouldCompareNumbers() {
        var result = execute("-1<2")
        assertTrue(result as Boolean)

        result = execute("1>-2")
        assertTrue(result as Boolean)
    }

    @Test
    fun shouldNegate() {
        var result = execute("!(-1<2)")
        assertFalse(result as Boolean)

        result = execute("!(1>-2)")
        assertFalse(result as Boolean)
    }

    @Test
    fun shouldCheckEquality() {
        var result = execute("3928==3928")
        assertTrue(result as Boolean)

        result = execute("-392==392")
        assertFalse(result as Boolean)
    }

    @Test
    fun shouldCheckNonEquality() {
        var result = execute("3927!=3928")
        assertTrue(result as Boolean)

        result = execute("-392!=392")
        assertTrue(result as Boolean)

        result = execute("392!=392")
        assertFalse(result as Boolean)

        result = execute("true!=false")
        assertTrue(result as Boolean)
    }

    @Test
    fun shouldCompareWithLogicalAnd() {
        var result = execute("(1<2)&&(1==1)")
        assertTrue(result as Boolean)

        result = execute("(1>2)&&(1==1)")
        assertFalse(result as Boolean)

        result = execute("(1>2)&&(1==2)")
        assertFalse(result as Boolean)
    }

    @Test
    fun shouldCompareWithLogicalOr() {
        var result = execute("(1<2)||(1==1)")
        assertTrue(result as Boolean)

        result = execute("(1>2)||(1==1)")
        assertTrue(result as Boolean)

        result = execute("(1>2)||(1==2)")
        assertFalse(result as Boolean)
    }

    @Test
    fun shouldCompareWithGreaterEquals() {
        var result = execute("2>=2")
        assertTrue(result as Boolean)

        result = execute("3>=2")
        assertTrue(result as Boolean)

        result = execute("1>=2")
        assertFalse(result as Boolean)
    }

    @Test
    fun shouldCompareWithLessEquals() {
        var result = execute("2<=2")
        assertTrue(result as Boolean)

        result = execute("3<=2")
        assertFalse(result as Boolean)

        result = execute("1<=2")
        assertTrue(result as Boolean)
    }

    @Test
    fun shouldErrorForInvalidBoolLogic() {
        assertThrows(DynamicSemanticException::class.java) { execute("true - false") }
        assertThrows(DynamicSemanticException::class.java) { execute("-false") }
    }

    @Test
    fun shouldHaveCorrectPrecedenceForEquals() {
        var result = execute("1 < 2 == true")
        assertTrue(result as Boolean)

        result = execute("true == 1 < 2")
        assertTrue(result as Boolean)
    }
}