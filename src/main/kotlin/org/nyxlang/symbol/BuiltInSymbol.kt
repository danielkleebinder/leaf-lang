package org.nyxlang.symbol

/**
 * Built in symbols are predefined symbols that are elementary (i.e. cannot
 * be further split into sub symbols).
 */
class BuiltInSymbol(name: String) : Symbol(name) {
    override fun toString() = "BuiltInSymbol(type=$name)"
}