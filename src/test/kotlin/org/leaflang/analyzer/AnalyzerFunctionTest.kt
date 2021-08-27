package org.leaflang.analyzer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.leaflang.TestSuit

class AnalyzerFunctionTest : TestSuit() {

    @Test
    fun shouldErrorForIncompatibleReturnType() {
        assertSemanticError { execute("fun c -> number = 10; const a: string = c()") }
    }

    @Test
    fun shouldInferTypeFromFunctionCorrectly() {
        assertDoesNotThrow { execute("fun x1 -> number = 10; const y1 = x1(); const z1: number = y1") }
        assertDoesNotThrow { execute("fun x2 -> string = 10; const y2 = x2(); const z2: string = y2") }
        assertDoesNotThrow { execute("fun x3 -> array = 10; const y3 = x3(); const z3: array = y3") }
    }
}