package org.pl.symbol

/**
 * A symbol table is an abstract data type that is used for tracking various
 * symbols occurring in the program code.
 */
interface ISymbolTable {

    /**
     * Defines a new [symbol] in the symbol table which is associated
     * with the given [identifier].
     */
    fun set(identifier: String, symbol: Symbol)

    /**
     * Returns the symbol associated with the given [identifier].
     */
    fun get(identifier: String): Symbol?

    /**
     * Returns true if a symbol with the given [identifier] exists, otherwise
     * false is returned.
     */
    fun has(identifier: String) = get(identifier) != null

    /**
     * Removes the symbol with the given [identifier] from this symbol table.
     */
    fun remove(identifier: String): Symbol?
}