package org.pl.symbol

/**
 * Implementation of the symbol table specification. The [parent] symbol table
 * is used to allow scoping with the symbol table.
 */
class SymbolTable(private val parent: ISymbolTable?) : ISymbolTable {

    private val symbols = hashMapOf<String, Symbol>()

    override fun set(identifier: String, symbol: Symbol) {
        symbols[identifier] = symbol
    }

    override fun get(identifier: String): Symbol? {
        val symbol = symbols[identifier]
        if (parent != null && symbol == null) {
            return parent.get(identifier)
        }
        return symbol
    }

    override fun remove(identifier: String) = symbols.remove(identifier)
    override fun toString() = "SymbolTable(parent=$parent, symbols=$symbols)"
}