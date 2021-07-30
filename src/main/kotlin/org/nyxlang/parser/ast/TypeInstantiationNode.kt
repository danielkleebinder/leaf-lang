package org.nyxlang.parser.ast

import org.nyxlang.analyzer.symbol.TypeSymbol

/**
 * Node that allows custom type declaration.
 */
class TypeInstantiationNode(val name: String,
                            val args: List<TypeArgument> = listOf(),
                            var spec: TypeSymbol? = null) : INode {
    override fun toString() = "TypeInstantiationNode(name=$name, args=$args)"
}