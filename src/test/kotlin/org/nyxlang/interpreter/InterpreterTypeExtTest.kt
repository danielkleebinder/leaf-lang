package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.analyzer.exception.StaticSemanticException

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
        assertThrows(StaticSemanticException::class.java) { execute("fun <>.test = true".trimIndent()) }
        assertThrows(StaticSemanticException::class.java) { execute("fun .test = true".trimIndent()) }
    }

    @Test
    fun shouldErrorOnInvalidExtTypes() {
        assertThrows(StaticSemanticException::class.java) { execute("fun <UnknownTypooo>.test = true") }
        assertThrows(StaticSemanticException::class.java) { execute("fun <string, UnknownTypooo>.test { return true }") }
    }
}