package org.nyxlang.parser.ast

/**
 * An async node that contains the node that should be executed asynchronously.
 */
class AsyncNode(val statement: INode) : INode {
    override fun toString() = "AsyncNode(statement=$statement)"
}