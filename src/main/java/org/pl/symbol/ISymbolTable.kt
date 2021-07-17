package org.pl.symbol

/**
 * A symbol table is an abstract data type that is used for tracking various
 * symbols occurring in the program code.
 */
interface ISymbolTable {

    /**
     * Defines a new [symbol] in the symbol table which is associated
     * with its name property. Returns the previous symbol with this name.
     */
    fun define(symbol: Symbol): Symbol?

    /**
     * Returns the symbol associated with the given [name].
     */
    fun get(name: String): Symbol?

    /**
     * Returns true if a symbol with the given [name] exists, otherwise
     * false is returned.
     */
    fun has(name: String) = get(name) != null

    /**
     * Removes the symbol with the given [name] from this symbol table.
     */
    fun remove(name: String): Symbol?
}