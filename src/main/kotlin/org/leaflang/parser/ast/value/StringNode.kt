package org.leaflang.parser.ast.value

import org.leaflang.parser.ast.INode

/**
 * String value.
 */
class StringNode(val value: String) : INode {
    override fun toString() = "StringNode{val=\"$value\"}"
}