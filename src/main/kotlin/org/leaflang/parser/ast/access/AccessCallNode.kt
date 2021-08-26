package org.leaflang.parser.ast.access

import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * Indicates that a function is being called with the given list of [args].
 */
data class AccessCallNode(override val position: NodePosition,
                          val args: List<INode>) : INode