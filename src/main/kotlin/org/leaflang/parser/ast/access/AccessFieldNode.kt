package org.leaflang.parser.ast.access

import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * Indicates an access on the custom type field with the given [name].
 */
data class AccessFieldNode(override val position: NodePosition,
                           val name: String) : INode