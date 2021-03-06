package org.leaflang.analyzer.symbol

import org.leaflang.natives.NativeFunction

/**
 * Built in function symbols represent native functions.
 */
class NativeFunSymbol(name: String,
                      var params: ArrayList<VarSymbol> = arrayListOf(),
                      var returns: Symbol? = null,
                      val nativeFunction: NativeFunction) : Symbol(name, 0) {
    override fun toString() = "NativeFunSymbol(name=$name, params=$params, returns=$returns)"
}