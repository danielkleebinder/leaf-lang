package org.nyxlang.parser.ast

/**
 * String value.
 */
class StringNode(val value: String) : INode {
    override fun toString() = "StringNode{val=\"$value\"}"
}