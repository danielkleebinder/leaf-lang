package org.nyxlang.analyzer

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.nyxlang.TestSuit
import org.nyxlang.analyzer.exception.StaticSemanticException

class AnalyzerVarTest : TestSuit() {

    @Test
    fun shouldErrorForIncompatibleAssignTypes() {
        assertThrows(StaticSemanticException::class.java) { execute("const a: string = 10") }
        assertThrows(StaticSemanticException::class.java) { execute("const b: number = true") }
        assertThrows(StaticSemanticException::class.java) { execute("const c: bool = [1,2,3]") }
        assertThrows(StaticSemanticException::class.java) { execute("const d: array = \"Hello World\"") }
    }

    @Test
    fun shouldErrorForIncompatibleVarTypes() {
        assertThrows(StaticSemanticException::class.java) { execute("const x1 = 10; var y1: string = x1") }
        assertThrows(StaticSemanticException::class.java) { execute("const x2 = \"Hello World\"; var y2: number = x2") }
        assertThrows(StaticSemanticException::class.java) { execute("const x3: bool = true; var y3: array = x3") }
    }

    @Test
    fun shouldInferTypeCorrectly() {
        assertDoesNotThrow { execute("const x1 = (10 == 3) || true; var y1: bool = x1") }
        assertDoesNotThrow { execute("const x2 = 10 * 3 - 1; var y2: number = x2") }
        assertDoesNotThrow { execute("const x3 = [10 * 3, true, \"Hey\"]; var y3: array = x3") }
    }
}