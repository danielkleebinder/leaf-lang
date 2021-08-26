package org.leaflang.parser.ast.value

import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * String value.
 */
class StringNode(override val position: NodePosition,
                 val value: String) : INode {
    override fun toString() = "StringNode{val=\"$value\"}"
}