package org.leaflang.interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.analyzer.exception.StaticSemanticException
import java.math.BigDecimal

class InterpreterStringTest : TestSuit() {

    @Test
    fun shouldConcatenate() {
        var result = execute("\"Hello\" + \"Strings\"")
        assertEquals("HelloStrings", result)

        result = execute("\"Hello\" + \" my \" + \"Strings\"")
        assertEquals("Hello my Strings", result)

        result = execute("\"This\" + \" is another \" + \"a bit\" + \" larger\" + \" Test\"")
        assertEquals("This is another a bit larger Test", result)
    }

    @Test
    fun shouldConcatenateFromVariables() {
        val result = execute("const a = \"Hello\", b = \"World\"; a+b")
        assertEquals("HelloWorld", result)
    }

    @Test
    fun shouldConcatenateInLoop() {
        val result = execute("var res=\"\", i=0; loop :i<3:i=i+1 { res=res+\"Test\" }; res")
        assertEquals("TestTestTest", result)
    }

    @Test
    fun shouldConcatenateWithNumbers() {
        var result = execute("\"Value:\" + 1")
        assertEquals("Value:1", result)

        result = execute("\"Value:\" + 1 + \"-\" + 2")
        assertEquals("Value:1-2", result)
    }

    @Test
    fun shouldConcatenateWithBools() {
        var result = execute("\"Value:\" + true")
        assertEquals("Value:true", result)

        result = execute("\"Value:\" + false + \"-\" + true")
        assertEquals("Value:false-true", result)
    }

    @Test
    fun shouldAccessLength() {
        assertEquals(BigDecimal.valueOf(0), execute("~\"\""))
        assertEquals(BigDecimal.valueOf(1), execute("~\"C\""))
        assertEquals(BigDecimal.valueOf(12), execute("~\"Hello World!\""))
        assertEquals(BigDecimal.valueOf(13), execute("const a = \"Hello World!!\"; ~a"))
    }

    @Test
    fun shouldAccessCharacters() {
        assertEquals("H", execute("const a = \"Hello World\"; a[0]"))
        assertEquals(" ", execute("const b = \"Hello World\"; b[5]"))
        assertEquals("!", execute("const c = \"Hello World!\"; c[11]"))
    }

    @Test
    fun shouldCheckEquality() {
        assertTrue(execute("\"Test\" == \"Test\"") as Boolean)
        assertTrue(execute("\"Test\" != \"Test2\"") as Boolean)
        assertTrue(execute("\"Test7Test\" != \"Test\"") as Boolean)
        assertTrue(execute("\"I am here!\" == \"I am here!\"") as Boolean)
    }

    @Test
    fun shouldErrorForWrongOperators() {
        assertSemanticError { execute("\"Test\" / 3") }
        assertSemanticError { execute("\"Test\" % 3") }
    }
}