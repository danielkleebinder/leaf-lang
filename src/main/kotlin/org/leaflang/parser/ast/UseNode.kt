package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * A "use" statement that allows importing code snippets from other files.
 */
class UseNode(override val position: NodePosition,
              val fileName: String) : INode {
    override fun toString() = "UseNode(file=\"$fileName\")"
}