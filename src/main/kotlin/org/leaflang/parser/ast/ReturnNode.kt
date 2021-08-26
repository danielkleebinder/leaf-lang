package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * Node that allows returning from a function.
 */
class ReturnNode(override val position: NodePosition,
                 val returns: INode) : INode {
    override fun toString() = "ReturnNode(returns=$returns)"
}