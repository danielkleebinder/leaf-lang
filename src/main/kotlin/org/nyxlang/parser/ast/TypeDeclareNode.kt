package org.nyxlang.parser.ast

/**
 * Node that allows custom type declaration.
 */
class TypeDeclareNode(val name: String?,
                      val vars: List<DeclarationsNode> = arrayListOf()) : INode {
    override fun toString() = "TypeDeclareNode(name=$name, vars=$vars)"
}