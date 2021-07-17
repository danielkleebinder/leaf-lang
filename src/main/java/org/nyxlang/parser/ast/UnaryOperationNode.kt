package org.nyxlang.parser.ast

/**
 * A unary operation node that contains some operand ([node]) and an operator ([op]).
 */
class UnaryOperationNode(val node: INode, val op: UnaryOperation) : INode {
    override fun toString() = "UnaryOperationNode{node=$node, op=$op}"
}