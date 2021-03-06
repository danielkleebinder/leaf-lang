package org.leaflang.parser.ast.access

import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * Access variables and constants with this node. It is also used
 * for field access in arrays and custom types.
 */
data class AccessNode(override val position: NodePosition,
                      val name: String,
                      val children: List<INode> = listOf()) : INode