package org.leaflang.parser.ast.type

import org.leaflang.analyzer.symbol.TypeSymbol
import org.leaflang.parser.ast.DeclarationsNode
import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition

/**
 * Node that allows custom type declaration.
 */
data class TypeDeclareNode(override val position: NodePosition,
                           val name: String,
                           val traits: List<String> = listOf(),
                           val fields: List<DeclarationsNode> = listOf(),
                           var spec: TypeSymbol? = null) : INode