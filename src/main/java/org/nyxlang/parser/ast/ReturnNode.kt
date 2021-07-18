package org.nyxlang.parser.ast

/**
 * Node that allows returning from a function.
 */
class ReturnNode(val returns: INode) : INode {
    override fun toString() = "ReturnNode(returns=$returns)"
}