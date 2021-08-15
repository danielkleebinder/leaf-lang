package org.leaflang.analyzer.symbol

/**
 * Built in symbols are predefined symbols that are elementary (i.e. cannot
 * be further split into sub symbols).
 */
class BuiltInSymbol(name: String) : Symbol(name, 0), ITypedSymbol {

    override val type: Symbol
        get() = this

    override fun toString() = "BuiltInSymbol(type=$name)"
}