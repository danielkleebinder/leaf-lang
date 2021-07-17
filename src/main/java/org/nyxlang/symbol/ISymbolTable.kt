package org.nyxlang.symbol

/**
 * A symbol table is an abstract data type that is used for tracking various
 * symbols occurring in the program code.
 */
interface ISymbolTable {

    /**
     * Parent symbol table.
     */
    val parent: ISymbolTable?

    /**
     * Optional symbol table name.
     */
    val name: String?

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
     * Returns the symbol associated with the given [name] if it exists in the local scope.
     */
    fun getLocal(name: String): Symbol?

    /**
     * Returns true if a symbol with the given [name] exists in the static
     * scope, otherwise false is returned.
     */
    fun has(name: String) = get(name) != null

    /**
     * Returns true if a symbol with the given [name] exists in the local
     * scope, otherwise false is returned.
     */
    fun hasLocal(name: String) = getLocal(name) != null

    /**
     * Removes the symbol with the given [name] from this symbol table.
     */
    fun remove(name: String): Symbol?
}