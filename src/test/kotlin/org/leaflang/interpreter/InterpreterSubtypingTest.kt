package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
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
        assertSemanticError { execute("type Human1; type Pet1; const a: Human1 = new Pet1") }
        assertSemanticError { execute("type Human2; const a: string = new Human2") }
        assertSemanticError { execute("trait Eats3; type Human3; const a: Eats3 = new Human3") }
    }

    @Test
    fun shouldAllowSubtypeReassignment() {
        assertDoesNotThrow {
            execute("""
                trait Drinks
                fun <Drinks>.drink()
                
                type Animal : Drinks
                fun <Animal>.drink = "Animal is drinking..."
                
                type Human : Drinks
                fun <Human>.drink = "Human is drinking..."
                
                var drinker: Drinks = new Animal
                drinker = new Human
            """.trimIndent())
        }
    }

    @Test
    fun shouldAllowPolymorphicCalls() {
        assertDoesNotThrow {
            execute("""
                trait Drinks
                fun <Drinks>.drink()
                
                type Animal : Drinks
                fun <Animal>.drink = "Animal is drinking..."
                
                type Human : Drinks
                fun <Human>.drink = "Human is drinking..."
                
                var drinker: Drinks

                drinker = new Animal
                const res1 = drinker.drink()
                
                drinker = new Human
                const res2 = drinker.drink()
            """.trimIndent())
        }
        assertEquals("Animal is drinking...", valueOf("res1"))
        assertEquals("Human is drinking...", valueOf("res2"))
    }

    @Test
    fun shouldNotAllowDifferentSignature() {
        assertSemanticError {
            execute("""
                trait Liquid
                trait Drinks
                fun <Drinks>.drink()
                
                type Animal : Drinks
                fun <Animal>.drink(liquid: Liquid) = "Animal is drinking " + liquid
            """.trimIndent())
        }
        assertSemanticError {
            execute("""
                trait Drinks2
                fun <Drinks2>.drink(n: number)
                
                type Animal2 : Drinks2
                fun <Animal2>.drink(n: string) = "Animal is drinking " + n
            """.trimIndent())
        }
    }

    @Test
    fun shouldAllowSubtypeFunctionParameters() {
        execute("""
                trait Animal
                type Cat : Animal
                fun feed(animal: Animal) = "Yummy, that was delicious"
                var cat = new Cat
                const res = feed(cat)
            """.trimIndent())
        assertEquals("Yummy, that was delicious", valueOf("res"))
    }
}