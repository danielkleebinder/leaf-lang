package org.leaflang.parser.ast

/**
 * The root node for a program.
 */
class ProgramNode(val statements: StatementListNode) : INode {
    override fun toString() = "ProgramNode{statements=$statements}"
}