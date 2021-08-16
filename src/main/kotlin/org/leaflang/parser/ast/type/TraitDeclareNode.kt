package org.leaflang.parser.ast.type

import org.leaflang.parser.ast.INode

/**
 * Node that allows trait declaration.
 */
data class TraitDeclareNode(val name: String) : INode