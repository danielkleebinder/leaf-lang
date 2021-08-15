package org.leaflang.parser.ast

/**
 * A single type argument for type instantiation.
 */
class TypeArgument(val name: String?, val valueExpr: INode) {
    override fun toString() = "TypeArgument{name=$name, value=$valueExpr}"
}