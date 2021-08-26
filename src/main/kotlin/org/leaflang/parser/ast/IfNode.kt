package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * A conditional if node that contains if cases with conditions and bodies
 * and an alternative else case.
 */
class IfNode(override val position: NodePosition,
             val cases: List<IfCase>,
             val elseCase: INode?) : INode {
    override fun toString() = "IfNode(cases=$cases, else=$elseCase)"
}