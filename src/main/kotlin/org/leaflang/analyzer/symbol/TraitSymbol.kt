package org.leaflang.analyzer.symbol

/**
 * Trait symbols represent user defined custom types.
 */
class TraitSymbol(name: String,
                  nestingLevel: Int = 0) : Symbol(name, nestingLevel) {
    override fun toString() = "TraitSymbol(name=$name)"
}