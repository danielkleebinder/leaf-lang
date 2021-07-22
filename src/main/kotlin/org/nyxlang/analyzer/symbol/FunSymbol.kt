package org.nyxlang.analyzer.symbol

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
                nestingLevel: Int = 0) : Symbol(name, nestingLevel) {
    override fun toString() = "FunSymbol(name=$name, params=$params, returns=$returns)"
}