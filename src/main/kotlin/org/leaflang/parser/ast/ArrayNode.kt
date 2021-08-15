package org.leaflang.parser.ast

/**
 * An array node that contains its elements as child nodes.
 */
class ArrayNode(val elements: List<INode>) : INode {
    override fun toString() = "ArrayNode(elements=$elements)"
}