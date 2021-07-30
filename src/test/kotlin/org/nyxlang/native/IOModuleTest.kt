package org.nyxlang.native

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit

class IOModuleTest : TestSuit() {

    @Test
    fun shouldPrint() {
        assertEquals("", withLocalOutStream { execute("print()") })
        assertEquals("Hello", withLocalOutStream { execute("print(\"Hello\")") })
        assertEquals("Hello${System.lineSeparator()}", withLocalOutStream { execute("println(\"Hello\")") })
    }

    @Test
    fun shouldClear() {
        assertDoesNotThrow { execute("clear()") }
    }

    @Test
    fun shouldReadFile() {
        execute("const res = readFile(\"src/test/resources/io/hello.txt\")")
        assertEquals("Hello World from File", valueOf("res"))
    }
}