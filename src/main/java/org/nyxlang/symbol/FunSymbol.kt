package org.nyxlang.symbol

import org.nyxlang.interpreter.memory.IActivationRecord
import org.nyxlang.parser.ast.INode

/**
 * Function symbols represent defined functions.
 */
class FunSymbol(name: String,
                var params: ArrayList<VarSymbol> = arrayListOf(),
                var returns: Symbol? = null,
                var requires: INode?,
                var ensures: INode?,
                var body: INode?,
                var staticScope: IActivationRecord? = null) : Symbol(name) {
    override fun toString() = "FunSymbol(name=$name, params=$params, returns=$returns)"
}