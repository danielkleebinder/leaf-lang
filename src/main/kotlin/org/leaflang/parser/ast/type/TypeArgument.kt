package org.leaflang.parser.ast.type

import org.leaflang.parser.ast.INode

/**
 * A single type argument for type instantiation.
 */
data class TypeArgument(val name: String?,
                        val valueExpr: INode)