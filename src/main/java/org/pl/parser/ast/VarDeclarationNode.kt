package org.pl.parser.ast

/**
 * Node that enables variable declaration for multiple variables at a time.
 *
 * Example:
 * `var a: bool, b = 10, c: number;`
 */
class VarDeclarationNode(val declarations: List<VarDeclaration>, vararg modifiers: Modifier) : INode {

    val modifiers = arrayListOf<Modifier>()

    init {
        this.modifiers.addAll(listOf(*modifiers))
    }

    override fun toString() = "VarDeclarationNode{declarations=$declarations, modifiers=$modifiers}"
}