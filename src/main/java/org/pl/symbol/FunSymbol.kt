package org.pl.symbol

/**
 * Function symbols represent defined functions.
 */
class FunSymbol(name: String) : Symbol(name) {
    override fun toString() = "FunSymbol(name=$name)"
}