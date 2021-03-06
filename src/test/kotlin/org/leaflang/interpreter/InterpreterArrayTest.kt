package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.interpreter.exception.DynamicSemanticException
import java.math.BigDecimal

class InterpreterArrayTest : TestSuit() {

    @Test
    fun shouldDeclareEmptyArray() {
        val result = execute("[]")
        assertNotNull(result)
        assertArrayEquals(arrayOfBigDecimal(), (result as List<*>).toTypedArray())
    }

    @Test
    fun shouldDeclareSimpleArray() {
        assertArrayEquals(arrayOfBigDecimal(1), (execute("[1]") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(1, 2), (execute("[1,2]") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(1, 2, 3), (execute("[1,2,3]") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(3, 1, 2), (execute("[3,1,2]") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(7, 8, 9, 2938), (execute("[7, 8 , 9, 2938]") as List<*>).toTypedArray())
    }

    @Test
    fun shouldDeclareWithExpressions() {
        assertArrayEquals(arrayOfBigDecimal(2), (execute("[1+1]") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(50, 75), (execute("[(3+7)*5, (5 * 6 * 5) / 2]") as List<*>).toTypedArray())
    }

    @Test
    fun shouldAddElement() {
        assertArrayEquals(arrayOfBigDecimal(7), (execute("[] + 7") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(1, 2, 7), (execute("[1,2] + 7") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(7, 1, 2), (execute("7 + [1,2]") as List<*>).toTypedArray())
    }

    @Test
    fun shouldAccessElements() {
        assertEquals(BigDecimal.valueOf(10), execute("const a = [10,20,30]; a[0]"))
        assertEquals(BigDecimal.valueOf(30), execute("const b = [10,20,30]; b[2]"))
    }

    @Test
    fun shouldAssignElements() {
        assertArrayEquals(arrayOfBigDecimal(999, 20, 30), (execute("const a = [10,20,30]; a[0] = 999; a") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(10, -74, 30), (execute("const b = [10,20,30]; b[1] = -74; b") as List<*>).toTypedArray())
    }

    @Test
    fun shouldAccessLength() {
        assertEquals(BigDecimal.valueOf(0), execute("~[]"))
        assertEquals(BigDecimal.valueOf(3), execute("~[5,6,7]"))
        assertEquals(BigDecimal.valueOf(6), execute("~[5,6,7,8,9,10]"))
        assertEquals(BigDecimal.valueOf(3), execute("const a = [8,9,10]; ~a"))
    }

    @Test
    fun shouldErrorOnIndexOutOfBounds() {
        assertThrows(DynamicSemanticException::class.java) { execute("const a = []; a[0]") }
        assertThrows(DynamicSemanticException::class.java) { execute("const b = [1]; b[-1]") }
        assertThrows(DynamicSemanticException::class.java) { execute("const c = [1,2]; c[3]") }
    }

    @Test
    fun shouldErrorOnInvalidDeclaration() {
        assertSyntaxError { execute("[") }
        assertSyntaxError { execute("[1,2,") }
        assertSyntaxError { execute("[[2") }
        assertSyntaxError { execute("[3[") }
    }

    @Test
    fun shouldDeclareArrayType() {
        assertArrayEquals(arrayOfBigDecimal(8, 9, 10), (execute("const a: array = [8,9,10]; a") as List<*>).toTypedArray())
        assertSemanticError { execute("const a: array, b = 10; a = b") }
    }
}