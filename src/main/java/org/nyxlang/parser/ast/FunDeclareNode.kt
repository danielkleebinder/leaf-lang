package org.nyxlang.parser.ast

import org.nyxlang.symbol.FunSymbol

/**
 * Node that allows function declaration and definition.
 */
class FunDeclareNode(val name: String?,
                     val params: VarDeclareNode?,
                     val requires: INode?,
                     val ensures: INode?,
                     val returns: TypeNode?,
                     val body: INode?,
                     var spec: FunSymbol? = null) : INode {
    override fun toString() = "FunDeclareNode(name=$name, params=$params, requires=$requires, ensures=$ensures, returns=$returns, body=$body)"
}