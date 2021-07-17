package org.nyxlang.parser.ast

/**
 * Node that enables variable declaration for multiple variables at a time.
 *
 * Example:
 * `var a: bool, b = 10, c: number;`
 */
class VarDeclareNode(val declarations: List<VarDeclaration>, vararg modifiers: Modifier) : INode {
    val modifiers = arrayListOf(*modifiers)
    override fun toString() = "VarDeclareNode{declarations=$declarations, modifiers=$modifiers}"
}