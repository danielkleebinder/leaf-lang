package org.nyxlang.analyzer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.nyxlang.TestSuit
import org.nyxlang.symbol.FunSymbol

class AnalyzerFunTest : TestSuit() {

    @Test
    fun shouldWriteFunToSymbolTable() {
        analyze("fun f -> number = x")
        assertTrue(globalSymbolTable.has("f"))

        val symbol = globalSymbolTable.get("f")
        assertSame(FunSymbol::class.java, symbol!!.javaClass)

        val funSymbol = (symbol as FunSymbol)
        assertEquals("f", funSymbol.name)
        assertEquals(0, funSymbol.params.size)
    }
}