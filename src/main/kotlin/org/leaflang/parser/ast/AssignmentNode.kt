package org.leaflang.parser.ast

import org.leaflang.parser.ast.access.AccessNode

/**
 * Node that enables assignments.
 */
class AssignmentNode(val accessNode: AccessNode,
                     val assignmentExpr: INode) : INode {
    override fun toString() = "AssignmentNode(access=$accessNode, assignment=$assignmentExpr)"
}