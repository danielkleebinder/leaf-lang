package org.leaflang.parser.ast

import org.leaflang.parser.ast.access.AccessNode
import org.leaflang.parser.utils.NodePosition

/**
 * Node that enables assignments.
 */
class AssignmentNode(override val position: NodePosition,
                     val accessNode: AccessNode,
                     val assignmentExpr: INode) : INode {
    override fun toString() = "AssignmentNode(access=$accessNode, assignment=$assignmentExpr)"
}