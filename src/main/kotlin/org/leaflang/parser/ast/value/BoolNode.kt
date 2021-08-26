package org.leaflang.parser.ast.value

import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * Single boolean value.
 */
class BoolNode(override val position: NodePosition,
               val value: Boolean) : INode {
    override fun toString() = "BoolNode{val=$value}"
}