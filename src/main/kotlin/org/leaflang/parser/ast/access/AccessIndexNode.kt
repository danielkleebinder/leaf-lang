package org.leaflang.parser.ast.access

import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * Indicates an index based access on the given [indexExpr].
 */
data class AccessIndexNode(override val position: NodePosition,
                           val indexExpr: INode) : INode