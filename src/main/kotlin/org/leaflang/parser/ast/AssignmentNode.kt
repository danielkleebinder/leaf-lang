package org.leaflang.parser.ast

/**
 * Node that enables assignments.
 */
class AssignmentNode(val accessNode: AccessNode,
                     val assignmentExpr: INode) : INode {
    override fun toString() = "AssignmentNode(access=$accessNode, assignment=$assignmentExpr)"
}