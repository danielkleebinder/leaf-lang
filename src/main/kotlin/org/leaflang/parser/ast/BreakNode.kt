package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * Node that allows breaking out of a loop.
 */
data class BreakNode(override val position: NodePosition) : INode {
    override fun toString() = "BreakNode"
}