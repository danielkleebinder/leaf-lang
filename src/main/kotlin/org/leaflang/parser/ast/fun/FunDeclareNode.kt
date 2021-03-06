package org.leaflang.parser.ast.`fun`

import org.leaflang.analyzer.symbol.ClosureSymbol
import org.leaflang.parser.ast.DeclarationsNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.type.TypeNode
import org.leaflang.parser.utils.NodePosition

/**
 * Node that allows function declaration and definition.
 */
class FunDeclareNode(override val position: NodePosition,
                     val extensionOf: List<TypeNode> = listOf(),
                     val name: String?,
                     val params: DeclarationsNode?,
                     val requires: INode?,
                     val ensures: INode?,
                     val returns: TypeNode?,
                     val body: INode?,
                     var spec: ClosureSymbol? = null) : INode {
    override fun toString() = "FunDeclareNode(for=$extensionOf, name=$name, params=$params, requires=$requires, ensures=$ensures, returns=$returns, body=$body)"
}