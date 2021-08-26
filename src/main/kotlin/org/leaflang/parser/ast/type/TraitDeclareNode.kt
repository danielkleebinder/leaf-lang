package org.leaflang.parser.ast.type

import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * Node that allows trait declaration.
 */
data class TraitDeclareNode(override val position: NodePosition,
                            val name: String) : INode