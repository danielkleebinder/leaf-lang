package org.leaflang.parser.ast

/**
 * The block node.
 */
class BlockNode(val statements: StatementListNode) : INode {
    override fun toString() = "BlockNode{statements=$statements}"
}