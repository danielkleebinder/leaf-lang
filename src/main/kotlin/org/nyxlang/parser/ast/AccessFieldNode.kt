package org.nyxlang.parser.ast

/**
 * Indicates an access on the custom type field with the given [name].
 */
class AccessFieldNode(val name: String) : INode {
    override fun toString() = "AccessFieldNode(name=$name)"
}