package org.leaflang.parser.ast.access

import org.leaflang.parser.ast.INode

/**
 * Indicates an access on the custom type field with the given [name].
 */
data class AccessFieldNode(val name: String) : INode