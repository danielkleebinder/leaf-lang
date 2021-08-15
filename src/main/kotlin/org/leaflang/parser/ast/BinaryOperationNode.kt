package org.leaflang.parser.ast

/**
 * Node with two child nodes and a binary operation.
 */
class BinaryOperationNode(val leftNode: INode, val rightNode: INode, val op: BinaryOperation) : INode {
    override fun toString() = "BinaryOperationNode{leftNode=$leftNode, rightNode=$rightNode, op=$op}"
}