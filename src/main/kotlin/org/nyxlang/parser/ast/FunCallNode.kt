package org.nyxlang.parser.ast

/**
 * Indicates that the function with the given [name] is being called.
 */
class FunCallNode(val name: String, val args: List<INode>) : INode {
    override fun toString() = "FunCallNode(name=$name, args=$args)"
}