package org.leaflang.analyzer.symbol

/**
 * A symbol is an abstract entity of the programming language that
 * can be addressed and referenced.
 */
open class Symbol(val name: String,
                  var nestingLevel: Int = 0) {

    /**
     * Checks if this symbol is a subtype of the trait with the given [name].
     */
    fun isSubtypeOf(name: String): Boolean {
        if (this.name == name) return true
        if (this is TypeSymbol) return traits.any { it.name == name }
        return false
    }

    override fun toString() = "Symbol(name=$name, nesting=$nestingLevel)"
}
