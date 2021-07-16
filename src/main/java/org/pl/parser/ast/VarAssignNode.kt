package org.pl.parser.ast

/**
 * Node that enables variable assignment.
 */
class VarAssignNode(val identifier: String, val assignmentExpr: INode) : INode {
    override fun toString() = "VarAssignNode{identifier=$identifier, assignment=$assignmentExpr}"
}