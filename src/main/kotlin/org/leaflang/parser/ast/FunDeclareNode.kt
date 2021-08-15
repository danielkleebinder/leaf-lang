package org.leaflang.parser.ast

import org.leaflang.analyzer.symbol.FunSymbol

/**
 * Node that allows function declaration and definition.
 */
class FunDeclareNode(val extensionOf: List<TypeNode> = listOf(),
                     val name: String?,
                     val params: DeclarationsNode?,
                     val requires: INode?,
                     val ensures: INode?,
                     val returns: TypeNode?,
                     val body: INode?,
                     var spec: FunSymbol? = null) : INode {
    override fun toString() = "FunDeclareNode(for=$extensionOf, name=$name, params=$params, requires=$requires, ensures=$ensures, returns=$returns, body=$body)"
}