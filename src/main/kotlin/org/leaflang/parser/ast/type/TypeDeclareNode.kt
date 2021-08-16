package org.leaflang.parser.ast.type

import org.leaflang.analyzer.symbol.TypeSymbol
import org.leaflang.parser.ast.DeclarationsNode
import org.leaflang.parser.ast.INode

/**
 * Node that allows custom type declaration.
 */
data class TypeDeclareNode(val name: String,
                           val fields: List<DeclarationsNode> = listOf(),
                           var spec: TypeSymbol? = null) : INode