package org.nyxlang.analyzer.symbol

/**
 * A symbol is an abstract entity of the programming language that
 * can be addressed and referenced.
 */
open class Symbol(val name: String,
                  var nestingLevel: Int = 0) {
    override fun toString() = "Symbol(name=$name, nesting=$nestingLevel)"
}