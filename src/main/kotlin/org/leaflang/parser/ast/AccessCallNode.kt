package org.leaflang.parser.ast

/**
 * Indicates that a function is being called with the given list of [args].
 */
class AccessCallNode(val args: List<INode>) : INode {
    override fun toString() = "AccessCallNode(args=$args)"
}