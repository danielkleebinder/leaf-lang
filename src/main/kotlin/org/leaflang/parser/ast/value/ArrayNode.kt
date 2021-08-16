package org.leaflang.parser.ast.value

import org.leaflang.parser.ast.INode

/**
 * An array node that contains its elements as child nodes.
 */
class ArrayNode(val elements: List<INode>) : INode {
    override fun toString() = "ArrayNode(elements=$elements)"
}