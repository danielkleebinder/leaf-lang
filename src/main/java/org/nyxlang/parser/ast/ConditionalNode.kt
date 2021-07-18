package org.nyxlang.parser.ast

/**
 * A conditional if node that contains if cases with conditions and bodies
 * and an alternative else case.
 */
class ConditionalNode(val cases: List<ConditionalCase>, val elseCase: INode?) : INode {
    override fun toString() = "ConditionalNode(cases=$cases, else=$elseCase)"
}