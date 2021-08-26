package org.leaflang.parser.ast.type

import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * Type attribute value of a language element.
 */
data class TypeNode(override val position: NodePosition,
                    val type: String) : INode