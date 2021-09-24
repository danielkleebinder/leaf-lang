package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.leaflang.TestSuit

class InterpreterTraitTest : TestSuit() {

    @Test
    fun shouldDeclareSimpleTrait() {
        assertDoesNotThrow { execute("trait Eats1") }
        assertDoesNotThrow { execute("trait Drinks2") }
        assertDoesNotThrow { execute("trait Eats3; trait Drinks3;") }
    }

    @Test
    fun shouldErrorWithTraitBlock() {
        assertSyntaxError { execute("trait Eats1 {}") }
        assertSyntaxError { execute("trait Eats2 { a: number }") }
    }

    @Test
    fun shouldAddAbstractFunction() {
        assertDoesNotThrow { execute("trait Eats1; fun <Eats1>.eat()") }
        assertDoesNotThrow { execute("trait Eats2; fun <Eats2>.eat(a: number) {}") }
        assertDoesNotThrow { execute("trait Eats3; trait Drinks3; fun <Drinks3, Eats3>.eat {}") }
    }

    @Test
    fun shouldImplementAbstractFunction() {
        execute("""
            trait Named;
            fun <Named>.name();
            
            type Pet : Named;
            fun <Pet>.name = "Bello";

            const pet = new Pet
            const res = pet.name()
        """.trimIndent())
        assertEquals("Bello", valueOf("res"))
    }

    @Test
    fun shouldErrorIfSameTraitIsImplementedMultipleTimes() {
        assertSemanticError { execute("trait Eats1; type Pet1 : Eats1, Eats1") }
        assertSemanticError { execute("trait Eats2; trait Walks2; type Pet2 : Eats2, Drinks2, Eats2") }
        assertSemanticError { execute("trait Eats3; trait Walks3; type Pet3 : Drinks3, Eats3, Eats3") }
    }

    @Test
    fun shouldErrorIfImplementedTraitDoesNotExist() {
        assertSemanticError { execute("type Pet1 : Eats1") }
        assertSemanticError { execute("trait Eats2; type Pet2 : Eatss") }
        assertSemanticError { execute("trait Eats3; type Pet3 : Eats3, Drinks3") }
    }

    @Test
    fun shouldErrorIfNotATrait() {
        assertSemanticError { execute("type Eats1; type Pet1 : Eats1") }
        assertSemanticError { execute("const a2 = 10; type Pet2 : a2") }
        assertSemanticError { execute("trait Eats3; const a3 = 10; type Pet3 : Eats3, a3") }
        assertSemanticError { execute("trait Eats4; const Drinks4 = 42; type Pet4 : Eats4, Drinks4") }
    }

    @Test
    fun shouldErrorIfTraitIsNotInScope() {
        assertSemanticError { execute("{ trait Eats1 }; type Pet1 : Eats1") }
        assertSemanticError { execute("trait Eats2; { trait Drinks2 }; type Pet2 : Eats2, Drinks2") }
    }

    @Test
    fun shouldErrorIfAbstractFunctionDeclaredWithoutTrait() {
        assertSyntaxError { execute("fun f1") }
        assertSyntaxError { execute("fun f2(a: number)") }
        assertSyntaxError { execute("fun f3(a: number) -> string") }
        assertSemanticError { execute("type Human4; fun <Human4>.f4") }
        assertSemanticError { execute("type Human5; fun <Human5>.f5(a: number)") }
        assertSemanticError { execute("type Human6; fun <Human6>.f6(a: number) -> string") }
        assertSemanticError { execute("trait Eats7; type Human7; fun <Eats7, Human7>.f7(a: number) -> string") }
    }

    @Test
    fun shouldErrorIfNonAbstractFunctionDeclaredOnTrait() {
        assertSemanticError { execute("trait Eats1; fun <Eats1>.f1 = 10") }
        assertSemanticError { execute("trait Eats2; fun <Eats2>.f1 { true }") }
        assertSemanticError { execute("trait Eats3; fun <Eats3>.f1(a: number) : a > 10 -> bool { return true }") }
    }

    @Test
    fun shouldErrorIfTraitIsBeingInstantiated() {
        assertSemanticError { execute("trait Eats1; new Eats1") }
        assertSemanticError { execute("trait Drinks2; new Drinks2") }
        assertSemanticError { execute("trait Eatable; trait Feedable; const a = new Feedable") }
    }

    @Test
    fun shouldErrorForNotImplementedExtFunction() {
        assertSemanticError {
            execute("""
                trait Executes
                fun <Executes>.execute
                type Computer : Executes
                new Computer
            """.trimIndent())
        }
    }
}