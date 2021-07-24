package org.nyxlang.parser.ast

/**
 * A conditional if node that contains if cases with conditions and bodies
 * and an alternative else case.
 */
class IfNode(val cases: List<IfCase>, val elseCase: INode?) : INode {
    override fun toString() = "IfNode(cases=$cases, else=$elseCase)"
}