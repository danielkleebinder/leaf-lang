package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * Node that enables variable declaration for multiple variables at a time.
 *
 * Example:
 * `a: bool, b = 10, c: number;`
 */
class DeclarationsNode(override val position: NodePosition,
                       val declarations: List<Declaration>,
                       vararg modifiers: Modifier) : INode {
    val modifiers = arrayListOf(*modifiers)
    override fun toString() = "DeclarationsNode(declarations=$declarations, modifiers=$modifiers)"
}