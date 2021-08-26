package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * Node with two child nodes and a binary operation.
 */
class BinaryOperationNode(override val position: NodePosition,
                          val leftNode: INode,
                          val rightNode: INode,
                          val op: BinaryOperation) : INode {
    override fun toString() = "BinaryOperationNode{leftNode=$leftNode, rightNode=$rightNode, op=$op}"
}