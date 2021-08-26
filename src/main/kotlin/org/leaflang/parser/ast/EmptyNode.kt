package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * Empty node (indicates end of program for example).
 */
class EmptyNode(override val position: NodePosition) : INode {
    override fun toString() = "EmptyNode"
}