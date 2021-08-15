package org.nyxlang.analyzer.symbol

import org.nyxlang.parser.ast.Modifier

/**
 * Variable symbols are variables that were defined with a certain
 * type and some modifiers.
 */
class VarSymbol(name: String,
                override val type: Symbol? = null,
                vararg modifiers: Modifier) : Symbol(name), ITypedSymbol {

    val modifiers = arrayListOf<Modifier>()

    init {
        this.modifiers.addAll(listOf(*modifiers))
    }

    override fun toString() = "VarSymbol(modifiers=$modifiers, name=$name)"
}