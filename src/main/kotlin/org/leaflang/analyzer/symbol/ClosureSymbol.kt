package org.leaflang.analyzer.symbol

import org.leaflang.parser.ast.INode

/**
 * Function symbols represent user defined functions.
 */
class ClosureSymbol(name: String? = null,
                    var params: ArrayList<VarSymbol> = arrayListOf(),
                    var returns: Symbol? = null,
                    var requires: INode? = null,
                    var ensures: INode? = null,
                    var body: INode? = null,
                    nestingLevel: Int = 0) : Symbol(name ?: "<anonymous>", nestingLevel) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ClosureSymbol
        if (params != other.params) return false
        if (returns != other.returns) return false
        return true
    }

    override fun hashCode(): Int {
        var result = params.hashCode()
        result = 31 * result + (returns?.hashCode() ?: 0)
        return result
    }

    override fun toString() = "ClosureSymbol(name=$name, params=$params)"
}