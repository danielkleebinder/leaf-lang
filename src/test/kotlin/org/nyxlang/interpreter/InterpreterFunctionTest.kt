package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.analyzer.exception.StaticSemanticException
import org.nyxlang.interpreter.exception.DynamicSemanticException
import java.math.BigDecimal

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
    fun shouldAllowVariableAssignment() {
//        try {
//            execute("fun a = true; const b = a; b()")
//        } catch (e: Exception) {
//            System.err.println(e)
//            fail()
//        }
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
    fun shouldAllowSimpleRecursion() {
        val program = """
            var a = 5
            fun rec = {
              a = a - 1
              if a > 0 { rec() }
            }
            rec()
        """.trimIndent()
        try {
            execute(program)
            assertEquals(BigDecimal.valueOf(0), globalActivationRecord["a"])
        } catch (e: Exception) {
            System.err.println(e)
            fail()
        }
    }

    @Test
    fun shouldComputeFactorialRecursively() {
        val result = execute(readResourceFile("fun-factorial.test.nyx"))
        assertEquals(BigDecimal.valueOf(3628800), result)
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

    @Test
    fun shouldFollowStaticLink1() {
        execute(readResourceFile("fun-static-link-1.test.nyx"))
        assertEquals(true, globalActivationRecord["res"])
    }

    @Test
    fun shouldFollowStaticLink2() {
        assertThrows(DynamicSemanticException::class.java) { execute(readResourceFile("fun-static-link-2.test.nyx")) }
    }

    @Test
    fun shouldFollowStaticLink3() {
        execute(readResourceFile("fun-static-link-3.test.nyx"))
        assertEquals(BigDecimal.valueOf(46), globalActivationRecord["res"])
    }

    @Test
    fun shouldFollowStaticLink4() {
        execute(readResourceFile("fun-static-link-4.test.nyx"))
        assertTrue(globalActivationRecord["res"] as Boolean)
    }

    @Test
    fun shouldFollowStaticLink5() {
        assertThrows(StaticSemanticException::class.java) { execute(readResourceFile("fun-static-link-5.test.nyx")) }
        assertNull(globalActivationRecord["res"])
    }

    @Test
    fun shouldErrorWhenRequiresNotFulfilled() {
        assertThrows(DynamicSemanticException::class.java) { execute("fun f(a: bool) : a { true }; f(false)") }
        assertThrows(DynamicSemanticException::class.java) { execute("fun f(a: number) : a > 0 { true }; f(0)") }
        assertThrows(DynamicSemanticException::class.java) { execute("fun f(a: number, b: number) : a + b > 5 { true }; f(1, 1)") }
    }

    @Test
    fun shouldErrorWhenEnsuresNotFulfilled() {
        assertThrows(DynamicSemanticException::class.java) { execute("fun f :: _ -> bool = false; f()") }
        assertThrows(DynamicSemanticException::class.java) { execute("fun f :: _ > 1 -> number = 0; f()") }
        assertThrows(DynamicSemanticException::class.java) { execute("fun f(a: bool) :: _ == a -> bool = true; f(false)") }
        assertThrows(DynamicSemanticException::class.java) { execute("fun f(a: number) :: ((_ - 1) == (a * a)) -> number = a * a; f(5)") }
    }

    @Test
    fun shouldErrorUnknownReturnType() {
        assertThrows(StaticSemanticException::class.java) { execute("fun f -> unknownType = false; f()") }
        assertThrows(StaticSemanticException::class.java) { execute("fun f -> Test = false; f()") }
        assertThrows(StaticSemanticException::class.java) { execute("fun f -> OString = false; f()") }
    }

    @Test
    fun shouldErrorWrongParamCount() {
        assertThrows(StaticSemanticException::class.java) { execute("fun f -> bool = false; f(1)") }
        assertThrows(StaticSemanticException::class.java) { execute("fun f(a: number) -> bool = false; f()") }
        assertThrows(StaticSemanticException::class.java) { execute("fun f(a: number) -> bool = false; f(1, 2)") }
    }

    @Test
    fun shouldAssignReturnValue() {
        execute(readResourceFile("fun-return-1.test.nyx"))
        assertEquals(BigDecimal.valueOf(771), globalActivationRecord["x"])
    }

    @Test
    fun shouldReturnLocalVariableValue() {
        execute(readResourceFile("fun-return-2.test.nyx"))
        assertEquals(BigDecimal.valueOf(11), globalActivationRecord["x"])
    }

    @Test
    fun shouldReturnBeforeEnd() {
        execute(readResourceFile("fun-return-3.test.nyx"))
        assertEquals(BigDecimal.valueOf(21), globalActivationRecord["c"])
    }

    @Test
    fun shouldContainMultipleReturns() {
        execute(readResourceFile("fun-return-4.test.nyx"))
        assertEquals(BigDecimal.valueOf(0), globalActivationRecord["x"])
        assertEquals(BigDecimal.valueOf(26), globalActivationRecord["y"])
    }

    @Test
    fun shouldReturnWithoutResult() {
        execute(readResourceFile("fun-return-5.test.nyx"))
        assertFalse(globalActivationRecord["res"] as Boolean)
    }

    @Test
    fun shouldReturnResultFromFunctionLoop() {
        execute(readResourceFile("fun-return-6.test.nyx"))
        assertEquals(BigDecimal.valueOf(205), globalActivationRecord["x"])
        assertEquals(BigDecimal.valueOf(305), globalActivationRecord["y"])
        assertEquals(BigDecimal.valueOf(0), globalActivationRecord["z"])
    }
}