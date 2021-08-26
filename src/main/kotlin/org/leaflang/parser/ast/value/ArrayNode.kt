package org.leaflang.parser.ast.value

import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * An array node that contains its elements as child nodes.
 */
class ArrayNode(override val position: NodePosition,
                val elements: List<INode>) : INode {
    override fun toString() = "ArrayNode(elements=$elements)"
}