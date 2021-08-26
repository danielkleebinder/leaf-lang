package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * Loops contain a [condition] statement which is evaluated based on [init] and [step] and the
 * loop [body] is executed.
 */
class LoopNode(override val position: NodePosition,
               val init: INode?,
               val condition: INode?,
               val step: INode?,
               val body: INode) : INode {
    override fun toString() = "LoopNode{init=$init, condition=$condition, step=$step, body=$body}"
}