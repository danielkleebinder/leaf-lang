package org.nyxlang.symbol

/**
 * Function symbols represent defined functions.
 */
class FunSymbol(name: String,
                var params: ArrayList<VarSymbol> = arrayListOf(),
                var returns: Symbol? = null) : Symbol(name) {
    override fun toString() = "FunSymbol(name=$name, returns=$returns)"
}