package org.leaflang.parser.ast.value

import org.leaflang.parser.ast.INode

/**
 * Single boolean value.
 */
class BoolNode(val value: Boolean) : INode {
    override fun toString() = "BoolNode{val=$value}"
}