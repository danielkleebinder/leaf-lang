package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * A statement list is a list of statement where each of them is
 * interpreted independently.
 */
class StatementListNode(override val position: NodePosition,
                        val statements: List<INode>) : INode {
    override fun toString() = "StatementListNode(statements=$statements)"
}