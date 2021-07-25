package org.nyxlang.analyzer.symbol

import org.nyxlang.parser.ast.INode

/**
 * Function symbols represent user defined functions.
 */
class FunSymbol(name: String?,
                var params: ArrayList<VarSymbol> = arrayListOf(),
                var returns: Symbol? = null,
                var requires: INode?,
                var ensures: INode?,
                var body: INode?,
                nestingLevel: Int = 0) : Symbol(name ?: "<anonymous>", nestingLevel) {

    override fun toString() = "FunSymbol(name=$name, params=$params, returns=$returns)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as FunSymbol
        if (params != other.params) return false
        if (returns != other.returns) return false
        return true
    }

    override fun hashCode(): Int {
        var result = params.hashCode()
        result = 31 * result + (returns?.hashCode() ?: 0)
        return result
    }
}