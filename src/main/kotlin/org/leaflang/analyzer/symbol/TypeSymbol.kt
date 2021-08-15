package org.leaflang.analyzer.symbol

/**
 * Type symbols represent user defined custom types.
 */
class TypeSymbol(name: String,
                 var fields: MutableList<VarSymbol> = arrayListOf(),
                 nestingLevel: Int = 0) : Symbol(name, nestingLevel) {
    override fun toString() = "TypeSymbol(name=$name, fields=$fields)"
}