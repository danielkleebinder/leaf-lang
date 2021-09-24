package org.leaflang.analyzer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.leaflang.TestSuit
import org.leaflang.analyzer.symbol.BuiltInSymbol
import org.leaflang.analyzer.symbol.ClosureSymbol
import org.leaflang.analyzer.symbol.SymbolTable
import org.leaflang.analyzer.symbol.VarSymbol

/**
 * Tests the symbols and symbol table.
 */
class SymbolTableTest : TestSuit() {

    @Test
    fun shouldHaveCorrectBuiltInType() {
        val sym = BuiltInSymbol("Int")
        assertEquals("Int", sym.type.name)
        assertNotNull(sym.toString())
    }

    @Test
    fun shouldCompareClosures() {
        assertTrue(ClosureSymbol(name = "fun1", returns = BuiltInSymbol("Int")) == ClosureSymbol(name = "fun2", returns = BuiltInSymbol("Int")))
        assertTrue(ClosureSymbol(name = "fun1", returns = BuiltInSymbol("Int")) == ClosureSymbol(name = "fun1", returns = BuiltInSymbol("Int")))
        assertFalse(ClosureSymbol(name = "fun1", returns = BuiltInSymbol("Int")) == ClosureSymbol(name = "fun1", returns = BuiltInSymbol("Bool")))
        assertTrue(ClosureSymbol(name = "fun1", returns = BuiltInSymbol("Int")).hashCode() == ClosureSymbol(name = "fun1", returns = BuiltInSymbol("Int")).hashCode())
        assertNotNull(ClosureSymbol(name = "fun1", returns = BuiltInSymbol("Int")).toString())
    }

    @Test
    fun shouldAddAndRemoveFromTable() {
        val table = SymbolTable("test")
        assertFalse(table.has("myVar"))

        table.define(VarSymbol("myVar"))
        assertTrue(table.has("myVar"))

        table.remove("myVar")
        assertFalse(table.has("myVar"))

        assertNotNull(table.toString())
    }
}