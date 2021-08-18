package org.leaflang.analyzer.symbol

/**
 * Trait symbols represent user defined custom types.
 */
class TraitSymbol(name: String,
                  nestingLevel: Int = 0,
                  val functions: MutableList<FunSymbol> = arrayListOf()) : Symbol(name, nestingLevel) {
    /**
     * Checks if a function with the given [funName] already exists.
     */
    fun hasFunction(funName: String?) = functions.any { it.name == funName }
    override fun toString() = "TraitSymbol(name=$name)"
}