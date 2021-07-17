package org.nyxlang.parser.ast

/**
 * Node that allows function declaration and definition.
 */
class FunDeclareNode(val name: String?,
                     val params: VarDeclareNode?,
                     val requires: INode?,
                     val ensures: INode?,
                     val returns: TypeNode?,
                     val body: INode?) : INode {
    override fun toString() = "FunDeclareNode(name=$name, params=$params, requires=$requires, ensures=$ensures, returns=$returns, body=$body)"
}