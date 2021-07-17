package org.nyxlang.parser.ast

/**
 * Type attribute value of a language element.
 */
class TypeNode(val type: String) : INode {
    override fun toString() = "TypeNode{type=$type}"
}