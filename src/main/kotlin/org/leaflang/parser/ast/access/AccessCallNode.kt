package org.leaflang.parser.ast.access

import org.leaflang.parser.ast.INode

/**
 * Indicates that a function is being called with the given list of [args].
 */
data class AccessCallNode(val args: List<INode>) : INode