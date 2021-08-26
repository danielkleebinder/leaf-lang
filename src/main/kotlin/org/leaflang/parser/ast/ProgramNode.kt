package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * The root node for a program.
 */
class ProgramNode(override val position: NodePosition,
                  val statements: StatementListNode) : INode {
    override fun toString() = "ProgramNode{statements=$statements}"
}