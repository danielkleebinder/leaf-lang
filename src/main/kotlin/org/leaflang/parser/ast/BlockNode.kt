package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * The block node.
 */
class BlockNode(override val position: NodePosition,
                val statements: StatementListNode) : INode {
    override fun toString() = "BlockNode{statements=$statements}"
}