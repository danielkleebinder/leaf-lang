package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * An async node that contains the node that should be executed asynchronously.
 */
class AsyncNode(override val position: NodePosition,
                val statement: INode) : INode {
    override fun toString() = "AsyncNode(statement=$statement)"
}