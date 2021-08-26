package org.leaflang.parser.ast.type

import org.leaflang.analyzer.symbol.TypeSymbol
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * Node that allows custom type declaration.
 */
data class TypeInstantiationNode(override val position: NodePosition,
                                 val name: String,
                                 val args: List<TypeArgument> = listOf(),
                                 var spec: TypeSymbol? = null) : INode