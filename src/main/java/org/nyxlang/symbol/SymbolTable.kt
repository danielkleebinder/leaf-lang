package org.nyxlang.symbol

/**
 * Implementation of the symbol table specification. The [parent] symbol table
 * is used to allow scoping with the symbol table.
 */
class SymbolTable(private val parent: ISymbolTable?) : ISymbolTable {

    private val symbols = hashMapOf<String, Symbol>()

    init {
        define(BuiltInSymbol("number"))
        define(BuiltInSymbol("bool"))
        define(BuiltInSymbol("string"))
    }

    override fun get(name: String): Symbol? {
        val symbol = symbols[name]
        if (parent != null && symbol == null) {
            return parent.get(name)
        }
        return symbol
    }

    override fun define(symbol: Symbol) = symbols.put(symbol.name, symbol)
    override fun remove(name: String) = symbols.remove(name)
    override fun toString() = "SymbolTable(parent=$parent, symbols=$symbols)"
}