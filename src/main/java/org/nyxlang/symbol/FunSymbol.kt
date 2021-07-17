package org.nyxlang.symbol

/**
 * Function symbols represent defined functions.
 */
class FunSymbol(name: String, val params: List<VarSymbol>, val returns: Symbol?) : Symbol(name) {
    override fun toString() = "FunSymbol(name=$name, returns=$returns)"
}