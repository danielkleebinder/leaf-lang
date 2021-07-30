package org.nyxlang.interpreter

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.nyxlang.TestSuit
import org.nyxlang.analyzer.exception.StaticSemanticException
import org.nyxlang.parser.exception.ParserException

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
        assertThrows(ParserException::class.java) { execute("type") }
    }

    @Test
    fun shouldErrorForTooManyArgs() {
        assertThrows(StaticSemanticException::class.java) { execute("type User; new User {name=\"Daniel\"}") }
        assertThrows(StaticSemanticException::class.java) { execute("type User { name: string }; new User {\"Daniel\", 777}") }
    }

    @Test
    fun shouldErrorIfFieldDoesNotExist() {
        assertThrows(StaticSemanticException::class.java) { execute("type User { name: string }; new User {nam=\"Daniel\"}") }
        assertThrows(StaticSemanticException::class.java) { execute("type User { name: string }; new User {namr=\"Daniel\"}") }
        assertThrows(StaticSemanticException::class.java) { execute("type User { age: number }; new User {name=\"Daniel\"}") }
        assertThrows(StaticSemanticException::class.java) { execute("type User { name: string, age: number }; new User {named=\"Daniel\", 10}") }
    }

    @Test
    fun shouldErrorForInvalidFieldType() {
        assertThrows(StaticSemanticException::class.java) { execute("type X{a:string}; new X{10}") }
        assertThrows(StaticSemanticException::class.java) { execute("type X{a:number}; new X{\"Hey\"}") }
        assertThrows(StaticSemanticException::class.java) { execute("type X{a:number,b:string}; new X{10,20}") }
    }
}