package org.nyxlang.parser.ast

/**
 * A conditional 'when' node that determines which code to execute depending on
 * the given clause.
 */
class WhenNode(val arg: INode?, val cases: List<WhenCase>, val elseCase: INode?) : INode {
    override fun toString() = "WhenNode(cases=$cases, else=$elseCase)"
}