package org.leaflang.parser.ast

/**
 * Indicates an index based access on the given [indexExpr].
 */
class AccessIndexNode(val indexExpr: INode) : INode {
    override fun toString() = "AccessIndexNode(index=$indexExpr)"
}