package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.leaflang.TestSuit
import java.math.BigDecimal

class InterpreterCustomTypeTest : TestSuit() {

    @Test
    fun shouldDeclareSimpleType() {
        assertDoesNotThrow { execute("type User1") }
        assertDoesNotThrow { execute("type User2;type Item1") }
        assertDoesNotThrow { execute("type myCustomType1") }
        assertDoesNotThrow { execute("type User3; type Item2; type Currency") }
        assertDoesNotThrow {
            execute("""
            type User4
            
            type Item3
        """.trimIndent())
        }
    }

    @Test
    fun shouldErrorIfNoTypeName() {
        execute("type")
        assertTrue(errorHandler.errorCount > 0)
    }

    @Test
    fun shouldErrorForTooManyArgs() {
        assertSemanticError { execute("type User1; new User1 {name=\"Daniel\"}") }
        assertSemanticError { execute("type User2 { name: string }; new User2 {\"Daniel\", 777}") }
    }

    @Test
    fun shouldErrorIfFieldDoesNotExist() {
        assertSemanticError { execute("type User1 { name: string }; new User1 {nam=\"Daniel\"}") }
        assertSemanticError { execute("type User2 { name: string }; new User2 {namr=\"Daniel\"}") }
        assertSemanticError { execute("type User3 { age: number }; new User3 {name=\"Daniel\"}") }
        assertSemanticError { execute("type User4 { name: string, age: number }; new User4 {named=\"Daniel\", 10}") }
    }

    @Test
    fun shouldErrorForInvalidFieldType() {
        assertSemanticError { execute("type X1{a:string}; new X1{10}") }
        assertSemanticError { execute("type X2{a:number}; new X2{\"Hey\"}") }
        assertSemanticError { execute("type X3{a:number,b:string}; new X3{10,20}") }
        assertSemanticError { execute("type X4{a:string}; new X4{a=10}") }
    }

    @Test
    fun shouldErrorForFieldRedeclaration() {
        assertSemanticError { execute("type X1{a:string,a:string}") }
        assertSemanticError { execute("type X2{a:string,a:number}") }
        assertSemanticError { execute("type X3{a:string,b:number,a:string}") }
    }

    @Test
    fun shouldAllowRecursiveDataTypeDeclaration() {
        assertDoesNotThrow { execute("type Human { name: string, parent: Human }") }
    }

    @Test
    fun shouldAssignAndAccessFields() {
        execute("type User { name: string }; const peter = new User { \"Peter\" }; const res1 = peter.name")
        assertEquals("Peter", valueOf("res1"))

        execute("const frank = new User { \"Daniel\" }; frank.name = \"Frank\"; const res2 = frank.name")
        assertEquals("Frank", valueOf("res2"))
    }

    @Test
    fun shouldUseAssignedFieldValues() {
        execute("type User { age = 25 }; const peter = new User; const res1 = peter.age")
        assertEquals(BigDecimal.valueOf(25), valueOf("res1"))
    }

    @Test
    fun shouldAccessDeepValue() {
        execute("""
            type Human {
              name: string
              mom: Human
              test = [-12]
            }
            const mom = new Human { "Anna" }
            const me = new Human { "Peter", mom }
            mom.test = mom.test + 72
            const res1 = me.mom.test[0]
            const res2 = me.mom.test[1]
        """.trimIndent())
        assertEquals(BigDecimal.valueOf(-12), valueOf("res1"))
        assertEquals(BigDecimal.valueOf(72), valueOf("res2"))
    }

    @Test
    fun shouldAccessLocalFields() {
        execute("""
            type Player {
              x = 10
              y = 20
              pos = object.x * object.y
            }
            const me = new Player
            const res = me.pos
        """.trimIndent())
        assertEquals(BigDecimal.valueOf(200), valueOf("res"))
    }

    @Test
    fun shouldErrorForSameFieldName() {
        assertSemanticError { execute("type X1{a=0, a=1}") }
        assertSemanticError { execute("type X2{x=10, y=20, pos=3, x=5}") }
    }
}