package org.leaflang.parser.ast

/**
 * Access variables and constants with this node. It is also used
 * for field access in arrays and custom types.
 */
class AccessNode(val name: String,
                 val children: List<INode> = listOf()) : INode {
    override fun toString() = "AccessNode(name=$name, children=$children)"
}