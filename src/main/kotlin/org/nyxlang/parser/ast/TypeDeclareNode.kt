package org.nyxlang.parser.ast

import org.nyxlang.analyzer.symbol.TypeSymbol

/**
 * Node that allows custom type declaration.
 */
class TypeDeclareNode(val name: String,
                      val fields: List<DeclarationsNode> = listOf(),
                      var spec: TypeSymbol? = null) : INode {
    override fun toString() = "TypeDeclareNode(name=$name, fields=$fields)"
}