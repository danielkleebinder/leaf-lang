package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.leaflang.TestSuit
import org.leaflang.analyzer.exception.StaticSemanticException

class InterpreterTraitTest : TestSuit() {

    @Test
    fun shouldDeclareSimpleTrait() {
        assertDoesNotThrow { execute("trait Eats") }
    }

    @Test
    fun shouldAddAbstractFunction() {
        assertDoesNotThrow { execute("trait Eats; fun <Eats>.eat()") }
    }

    @Test
    fun shouldErrorIfSameTraitIsImplementedMultipleTimes() {
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats; type Pet : Eats, Eats") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats; trait Walks; type Pet : Eats, Drinks, Eats") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats; trait Walks; type Pet : Drinks, Eats, Eats") }
    }

    @Test
    fun shouldErrorIfImplementedTraitDoesNotExist() {
        assertThrows(StaticSemanticException::class.java) { execute("type Pet : Eats") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats; type Pet : Eatss") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats; type Pet : Eats, Drinks") }
    }

    @Test
    fun shouldErrorIfNotATrait() {
        assertThrows(StaticSemanticException::class.java) { execute("type Eats; type Pet : Eats") }
        assertThrows(StaticSemanticException::class.java) { execute("const a = 10; type Pet : a") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats; const a = 10; type Pet : Eats, a") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats; const Drinks = 42; type Pet : Eats, Drinks") }
    }

    @Test
    fun shouldErrorIfTraitIsNotInScope() {
        assertThrows(StaticSemanticException::class.java) { execute("{ trait Eats }; type Pet : Eats") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats; { trait Drinks }; type Pet : Eats, Drinks") }
    }
}