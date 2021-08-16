package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.leaflang.TestSuit
import org.leaflang.analyzer.exception.StaticSemanticException

class InterpreterSubtypingTest : TestSuit() {

    @Test
    fun shouldAllowSubtypingWithTraits() {
        assertDoesNotThrow { execute("trait Eats1; type Pet1 : Eats1; const bello: Eats1 = new Pet1") }
    }

    @Test
    fun shouldNotAllowSubtypingForDifferentTypes() {
        Assertions.assertThrows(StaticSemanticException::class.java) { execute("type Human1; type Pet1; const a: Human1 = new Pet1") }
        Assertions.assertThrows(StaticSemanticException::class.java) { execute("type Human2; const a: string = new Human2") }
        Assertions.assertThrows(StaticSemanticException::class.java) { execute("trait Eats3; type Human3; const a: Eats3 = new Human3") }
    }
}