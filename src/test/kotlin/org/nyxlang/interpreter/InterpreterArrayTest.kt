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
        assertArrayEquals(arrayOf(), result as Array<*>)
    }

    @Test
    fun shouldDeclareSimpleArray() {
        assertArrayEquals(arrayOf(1), execute("[1]") as Array<*>)
        assertArrayEquals(arrayOf(1, 2), execute("[1,2]") as Array<*>)
        assertArrayEquals(arrayOf(1, 2, 3), execute("[1,2,3]") as Array<*>)
        assertArrayEquals(arrayOf(3, 1, 2), execute("[3,1,2]") as Array<*>)
        assertArrayEquals(arrayOf(7, 8, 9, 2938), execute("[7, 8 , 9, 2938]") as Array<*>)
    }

    @Test
    fun shouldDeclareArrayWithExpressions() {
        assertArrayEquals(arrayOf(1), execute("[1+1]") as Array<*>)
        assertArrayEquals(arrayOf(50, 75), execute("[(3+7)*5, (5 * 6 * 5) / 2]") as Array<*>)
    }

    @Test
    fun shouldAddElementToArray() {
        assertArrayEquals(arrayOf(7), execute("[] + 7") as Array<*>)
        assertArrayEquals(arrayOf(1, 2, 7), execute("[1,2] + 7") as Array<*>)
        assertArrayEquals(arrayOf(7, 1, 2), execute("7 + [1,2]") as Array<*>)
    }

    @Test
    fun shouldErrorOnInvalidArrayDeclaration() {
        assertThrows(ParserException::class.java) { execute("[") }
        assertThrows(ParserException::class.java) { execute("[1,2,") }
        assertThrows(ParserException::class.java) { execute("1,2,]]") }
        assertThrows(ParserException::class.java) { execute("[[2") }
        assertThrows(ParserException::class.java) { execute("[3[") }
    }
}