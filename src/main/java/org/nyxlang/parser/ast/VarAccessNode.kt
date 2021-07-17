package org.nyxlang.parser.ast

/**
 * Access variables and constants with this node.
 */
class VarAccessNode(val identifier: String) : INode {
    override fun toString() = "AccessNode{identifier=$identifier}"
}