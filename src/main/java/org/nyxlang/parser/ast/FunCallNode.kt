package org.nyxlang.parser.ast

import org.nyxlang.symbol.FunSymbol

/**
 * Indicates that the function with the given [name] is being called.
 */
class FunCallNode(val name: String, val args: List<INode>, var spec: FunSymbol? = null) : INode {
    override fun toString() = "FunCallNode(name=$name, args=$args, spec=$spec)"
}