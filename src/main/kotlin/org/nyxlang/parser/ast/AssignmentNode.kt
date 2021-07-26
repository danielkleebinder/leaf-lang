package org.nyxlang.parser.ast

/**
 * Node that enables assignments.
 */
class AssignmentNode(val name: String,
                     val assignmentExpr: INode,
                     val offsetExpr: INode? = null) : INode {
    override fun toString() = "AssignmentNode(name=$name, assignment=$assignmentExpr, offset=$offsetExpr)"
}