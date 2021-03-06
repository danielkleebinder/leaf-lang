package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import java.math.BigDecimal

/**
 * Tests certain type extension mechanisms.
 */
class InterpreterTypeExtTest : TestSuit() {

    @Test
    fun shouldAddExtFunction() {
        execute("""
            type Human;
            fun <Human>.name = "Daniel";
            const me = new Human;
            const res = me.name()
        """.trimIndent())
        assertEquals("Daniel", valueOf("res"))
    }

    @Test
    fun shouldAddMultipleExtFunctions() {
        execute("""
            type Human;
            fun <Human>.getFirstname() { return "Daniel" }
            fun <Human>.getLastname() { return "Kleebinder"}
            const me = new Human;
            const res = me.getFirstname() + " " + me.getLastname()
        """.trimIndent())
        assertEquals("Daniel Kleebinder", valueOf("res"))
    }

    @Test
    fun shouldAddExtFunctionToDifferentTypes() {
        execute("""
            type Pet
            type Human
            fun <Human, Pet>.name = "Now, that's difficult..."
            
            const me = new Human
            const pet = new Pet
            
            const res1 = me.name()
            const res2 = pet.name()
        """.trimIndent())
        assertEquals("Now, that's difficult...", valueOf("res1"))
        assertEquals("Now, that's difficult...", valueOf("res2"))
    }

    @Test
    fun shouldDeclareExtFunctionWithParams() {
        execute("""
            type Human { name: string }
            fun <Human>.sayHi(to: Human) -> string {
              return object.name + " says hi to " + to.name
            }
            const me = new Human { "Peter Peterson" }
            const result = me.sayHi(new Human { "Anna Anderson" })
        """.trimIndent())
        assertEquals("Peter Peterson says hi to Anna Anderson", valueOf("result"))
    }

    @Test
    fun shouldErrorIfNoExtensionsSpecified() {
        assertSyntaxError { execute("fun <>.test = true") }
        assertSyntaxError { execute("fun .test = true") }
    }

    @Test
    fun shouldErrorOnInvalidExtTypes() {
        assertSemanticError { execute("fun <UnknownTypooo>.test = true") }
        assertSemanticError { execute("fun <string, UnknownTypooo>.test { return true }") }
    }

    @Test
    fun shouldTypeInferObjectVariable() {
        assertDoesNotThrow {
            execute("""
            type Stream { data: array, other: Stream }
            fun <Stream>.test -> number {
              const i = 1
              const t1 = object.data[0]
              const t2 = object.other.data[i]
              return t1 + t2
            }
            const stream = new Stream { [10, 20], new Stream { [20, 30] } }
            const res = stream.test()
        """.trimIndent())
        }
        assertEquals(BigDecimal.valueOf(40), valueOf("res"))
    }

    @Test
    fun shouldOverloadFunction() {
        assertDoesNotThrow {
            execute("""
                type Stream
                fun <Stream>.max = max(1,2)
                const stream = new Stream
                const res = stream.max()
            """.trimIndent())
        }
        assertEquals(BigDecimal.valueOf(2), valueOf("res"))
    }
}