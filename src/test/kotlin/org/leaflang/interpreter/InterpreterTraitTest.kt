package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.leaflang.TestSuit
import org.leaflang.analyzer.exception.StaticSemanticException

class InterpreterTraitTest : TestSuit() {

    @Test
    fun shouldDeclareSimpleTrait() {
        assertDoesNotThrow { execute("trait Eats1") }
        assertDoesNotThrow { execute("trait Drinks2") }
        assertDoesNotThrow { execute("trait Eats3; trait Drinks3;") }
    }

    @Test
    fun shouldErrorWithTraitBlock() {
        assertTrue(withErrors { execute("trait Eats1 {}") } > 0)
        assertTrue(withErrors { execute("trait Eats2 { a: number }") } > 0)
    }

    @Test
    fun shouldAddAbstractFunction() {
        assertDoesNotThrow { execute("trait Eats1; fun <Eats1>.eat()") }
        assertDoesNotThrow { execute("trait Eats2; fun <Eats2>.eat(a: number) {}") }
        assertDoesNotThrow { execute("trait Eats3; trait Drinks3; fun <Drinks3, Eats3>.eat {}") }
    }

    @Test
    fun shouldErrorIfSameTraitIsImplementedMultipleTimes() {
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats1; type Pet1 : Eats1, Eats1") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats2; trait Walks2; type Pet2 : Eats2, Drinks2, Eats2") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats3; trait Walks3; type Pet3 : Drinks3, Eats3, Eats3") }
    }

    @Test
    fun shouldErrorIfImplementedTraitDoesNotExist() {
        assertThrows(StaticSemanticException::class.java) { execute("type Pet1 : Eats1") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats2; type Pet2 : Eatss") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats3; type Pet3 : Eats3, Drinks3") }
    }

    @Test
    fun shouldErrorIfNotATrait() {
        assertThrows(StaticSemanticException::class.java) { execute("type Eats1; type Pet1 : Eats1") }
        assertThrows(StaticSemanticException::class.java) { execute("const a2 = 10; type Pet2 : a2") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats3; const a3 = 10; type Pet3 : Eats3, a3") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats4; const Drinks4 = 42; type Pet4 : Eats4, Drinks4") }
    }

    @Test
    fun shouldErrorIfTraitIsNotInScope() {
        assertThrows(StaticSemanticException::class.java) { execute("{ trait Eats1 }; type Pet1 : Eats1") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats2; { trait Drinks2 }; type Pet2 : Eats2, Drinks2") }
    }

    @Test
    fun shouldErrorIfAbstractFunctionDeclaredWithoutTrait() {
        assertTrue(withErrors { execute("fun f1") } > 0)
        assertTrue(withErrors { execute("fun f2(a: number)") } > 0)
        assertTrue(withErrors { execute("fun f3(a: number) -> string") } > 0)
        assertThrows(StaticSemanticException::class.java) { execute("type Human4; fun <Human4>.f4") }
        assertThrows(StaticSemanticException::class.java) { execute("type Human5; fun <Human5>.f5(a: number)") }
        assertThrows(StaticSemanticException::class.java) { execute("type Human6; fun <Human6>.f6(a: number) -> string") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats7; type Human7; fun <Eats7, Human7>.f7(a: number) -> string") }
    }

    @Test
    fun shouldErrorIfNonAbstractFunctionDeclaredOnTrait() {
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats1; fun <Eats1>.f1 = 10") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats2; fun <Eats2>.f1 { true }") }
        assertThrows(StaticSemanticException::class.java) { execute("trait Eats3; fun <Eats3>.f1(a: number) : a > 10 -> bool { return true }") }
    }

    @Test
    fun shouldAllowSubtyping(){
        assertDoesNotThrow { execute("trait Eats1; type Pet1 : Eats1; const bello: Eats1 = new Pet1") }
    }
}