package org.leaflang.parser.ast.type

import org.leaflang.analyzer.symbol.TypeSymbol
import org.leaflang.parser.ast.INode

/**
 * Node that allows custom type declaration.
 */
data class TypeInstantiationNode(val name: String,
                                 val args: List<TypeArgument> = listOf(),
                                 var spec: TypeSymbol? = null) : INode