package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.parser.exception.ParserException

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
    fun shouldDeclareArrayWithExpressions() {
        assertArrayEquals(arrayOfBigDecimal(2), (execute("[1+1]") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(50, 75), (execute("[(3+7)*5, (5 * 6 * 5) / 2]") as List<*>).toTypedArray())
    }

    @Test
    fun shouldAddElementToArray() {
        assertArrayEquals(arrayOfBigDecimal(7), (execute("[] + 7") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(1, 2, 7), (execute("[1,2] + 7") as List<*>).toTypedArray())
        assertArrayEquals(arrayOfBigDecimal(7, 1, 2), (execute("7 + [1,2]") as List<*>).toTypedArray())
    }

    @Test
    fun shouldErrorOnInvalidArrayDeclaration() {
        assertThrows(ParserException::class.java) { execute("[") }
        assertThrows(ParserException::class.java) { execute("[1,2,") }
        assertThrows(ParserException::class.java) { execute("[[2") }
        assertThrows(ParserException::class.java) { execute("[3[") }
    }
}