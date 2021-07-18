package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.analyzer.exception.StaticSemanticException

class InterpreterFunctionTest : TestSuit() {

    @Test
    fun shouldErrorWhenRequiresWithoutParams() {
        assertThrows(StaticSemanticException::class.java) { execute("fun a : true -> number = 10") }
        assertThrows(StaticSemanticException::class.java) { execute("fun b() : true -> number = 10") }
        assertThrows(StaticSemanticException::class.java) { execute("fun c ( ) : false -> bool = true") }
    }

    @Test
    fun shouldErrorWhenEnsuresWithoutReturn() {
        assertThrows(StaticSemanticException::class.java) { execute("fun a :: true = 10") }
        assertThrows(StaticSemanticException::class.java) { execute("fun b :: _ > 10 && true = 10") }
        assertThrows(StaticSemanticException::class.java) { execute("fun c(x: number) :: x > 10 = 10") }
        assertThrows(StaticSemanticException::class.java) { execute("fun d(x: number) : x == 1 : _ != 10 = 10") }
    }

    @Test
    fun shouldDeclareFunctionsWithoutError() {
        try {
            execute("fun a = true")
            execute("fun b { true }")
            execute("fun c -> bool = true")
            execute("fun d (a: number) -> number = a")
            execute("fun e (a: number) : a > 0 = a")
            execute("fun f (a: number) : a > 0 : _ == a -> number = a")
            execute("fun g (a: number) :: _ == a -> number = a")
            execute("fun h (a: number, b: bool) { a == 1 && b }")
            execute("fun i(a: number) :: _ > 0 -> number = a")
            execute("fun j(x: number, y: number) : (x > y) : (x * y) -> number = x * y")
            execute("fun k(x: number, y: number) :: (x * y) -> number = x * y")
            execute("fun l(x: number, y: number) -> number = x * y")
        } catch (e: Exception) {
            System.err.println(e)
            fail()
        }
    }

    @Test
    fun shouldAllowRecursion() {
        val program = """
            fun test(a: number) {
              if a > 0 { test(a - 1) }
            }
            test(5)
        """.trimIndent()
        try {
            execute(program)
        } catch (e: Exception) {
            System.err.println(e)
            fail()
        }
    }

    @Test
    fun shouldAllowVariableRedeclaration() {
        val program = """
            fun test(a: number) {}
            const a = 10
            test(a)
        """.trimIndent()
        try {
            execute(program)
        } catch (e: Exception) {
            System.err.println(e)
            fail()
        }
    }
}