package org.nyxlang.analyzer.symbol

/**
 * Built in symbols are predefined symbols that are elementary (i.e. cannot
 * be further split into sub symbols).
 */
class BuiltInSymbol(name: String) : Symbol(name, 0) {
    override fun toString() = "BuiltInSymbol(type=$name)"
}