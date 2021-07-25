package org.nyxlang.native

import org.nyxlang.analyzer.symbol.NativeFunSymbol

/**
 * A module that provides native functions for immediate access inside
 * the programming language.
 */
interface INativeModule {

    /**
     * The name of the module. The system will load modules based on this identifier.
     */
    val name: String

    /**
     * Contains all defined functions from this particular module.
     */
    val functions: List<NativeFunSymbol>
}