package org.nyxlang.analyzer.symbol

/**
 * A typed symbol is a symbol which has a type attached to it.
 */
interface ITypedSymbol {

    /**
     * The symbols type.
     */
    val type: Symbol?
}