package org.pl.symbol

import org.pl.parser.ast.Modifier

/**
 * A symbol is an abstract entity of the programming language that
 * can be addressed and referenced.
 */
class Symbol(val name: String, val type: String?, val modifier: Array<Modifier>?) {
    override fun toString() = "Symbol(modifier=$modifier, name='$name', type=$type)"
}