package org.nyxlang.parser.ast

/**
 * Node that enables variable declaration for multiple variables at a time.
 *
 * Example:
 * `var a: bool, b = 10, c: number;`
 */
class DeclarationsNode(val declarations: List<Declaration>, vararg modifiers: Modifier) : INode {
    val modifiers = arrayListOf(*modifiers)
    override fun toString() = "DeclarationsNode{declarations=$declarations, modifiers=$modifiers}"
}