package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.*
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
            const res = me.getFirstname() + me.getLastname()
        """.trimIndent())
        assertEquals("Daniel Kleebinder", valueOf("res"))
    }

    @Test
    fun shouldAddExtFunctionToDifferentTypes() {
        execute("""
            type Pet
            type Human
            fun <Human, Pet>.name = "That's difficult now..."
            
            const me = new Human
            const pet = new Pet
            
            const res1 = me.name()
            const res2 = pet.name()
        """.trimIndent())
        assertEquals("That's difficult now...", valueOf("res1"))
        assertEquals("That's difficult now...", valueOf("res2"))
    }

    @Test
    fun shouldErrorIfNoExtensionsSpecified() {
        assertTrue(withErrors { execute("fun <>.test = true".trimIndent()) } > 0)
        assertTrue(withErrors { execute("fun .test = true".trimIndent()) } > 0)
    }

    @Test
    fun shouldErrorOnInvalidExtTypes() {
        assertThrows(StaticSemanticException::class.java) { execute("fun <UnknownTypooo>.test = true") }
        assertThrows(StaticSemanticException::class.java) { execute("fun <string, UnknownTypooo>.test { return true }") }
    }
}