package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * A unary operation node that contains some operand ([node]) and an operator ([op]).
 */
class UnaryOperationNode(override val position: NodePosition,
                         val node: INode,
                         val op: UnaryOperation) : INode {
    override fun toString() = "UnaryOperationNode{node=$node, op=$op}"
}