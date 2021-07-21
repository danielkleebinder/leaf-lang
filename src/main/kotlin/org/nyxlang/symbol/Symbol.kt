package org.nyxlang.symbol

/**
 * A symbol is an abstract entity of the programming language that
 * can be addressed and referenced.
 */
open class Symbol(val name: String) {
    override fun toString() = "Symbol(name=$name)"
}