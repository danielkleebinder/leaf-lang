package org.nyxlang.parser.ast

/**
 * Access variables and constants with this node.
 */
class AccessNode(val name: String,
                 val offsetExpr: INode? = null) : INode {
    override fun toString() = "AccessNode(name=$name, offset=$offsetExpr)"
}