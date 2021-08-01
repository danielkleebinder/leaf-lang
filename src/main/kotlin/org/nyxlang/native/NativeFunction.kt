package org.nyxlang.native

import org.nyxlang.analyzer.symbol.NativeFunSymbol
import org.nyxlang.analyzer.symbol.Symbol
import org.nyxlang.analyzer.symbol.VarSymbol
import org.nyxlang.interpreter.memory.cell.IMemoryCell


/**
 * A native function is a function that is invoked by the runtime system of the
 * programming language and ran by the native system environment. The given list
 * of arguments are used as input parameters and the native function is allowed to
 * return exactly one value.
 */
typealias NativeFunction = (args: Array<IMemoryCell?>) -> IMemoryCell?

/**
 * Encapsulates an input parameter for a native function.
 */
class NativeParam(val name: String,
                  val type: String? = null,
                  val default: String? = null)

/**
 * Builds a native function symbol
 */
fun nativeFunSymbol(name: String, params: Array<NativeParam>, func: NativeFunction, returnType: String? = null): NativeFunSymbol {
    return NativeFunSymbol(
            name = name,
            nativeFunction = func,
            params = arrayListOf(*params
                    .map { VarSymbol(it.name, if (it.type != null) Symbol(it.type, 0) else null) }
                    .toTypedArray()),
            returns = if (returnType != null) Symbol(returnType, 0) else null)
}