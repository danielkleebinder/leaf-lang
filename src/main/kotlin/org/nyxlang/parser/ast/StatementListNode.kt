package org.nyxlang.parser.ast

/**
 * A statement list is a list of statement where each of them is
 * interpreted independently.
 */
class StatementListNode(val statements: List<INode>) : INode {
    override fun toString() = "StatementListNode(statements=$statements)"
}