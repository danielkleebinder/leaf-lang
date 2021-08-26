package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * Node that allows continuing a loop.
 */
class ContinueNode(override val position: NodePosition) : INode {
    override fun toString() = "ContinueNode"
}