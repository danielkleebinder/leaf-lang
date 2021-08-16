package org.leaflang.parser.ast.access

import org.leaflang.parser.ast.INode

/**
 * Indicates an index based access on the given [indexExpr].
 */
data class AccessIndexNode(val indexExpr: INode) : INode