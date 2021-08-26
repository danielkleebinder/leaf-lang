package org.leaflang.analyzer

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.leaflang.TestSuit
import org.leaflang.analyzer.exception.StaticSemanticException

class AnalyzerVarTest : TestSuit() {

    @Test
    fun shouldErrorForIncompatibleAssignTypes() {
        assertSemanticError { execute("const a: string = 10") }
        assertSemanticError { execute("const b: number = true") }
        assertSemanticError { execute("const c: bool = [1,2,3]") }
        assertSemanticError { execute("const d: array = \"Hello World\"") }
    }

    @Test
    fun shouldErrorForIncompatibleVarTypes() {
        assertSemanticError { execute("const x1 = 10; var y1: string = x1") }
        assertSemanticError { execute("const x2 = \"Hello World\"; var y2: number = x2") }
        assertSemanticError { execute("const x3: bool = true; var y3: array = x3") }
    }

    @Test
    fun shouldInferTypeCorrectly() {
        assertDoesNotThrow { execute("const x1 = (10 == 3) || true; var y1: bool = x1") }
        assertDoesNotThrow { execute("const x2 = 10 * 3 - 1; var y2: number = x2") }
        assertDoesNotThrow { execute("const x3 = [10 * 3, true, \"Hey\"]; var y3: array = x3") }
    }
}