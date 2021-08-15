package org.leaflang.parser.ast

/**
 * Loops contain a [condition] statement which is evaluated based on [init] and [step] and the
 * loop [body] is executed.
 */
class LoopNode(val init: INode?, val condition: INode?, val step: INode?, val body: INode) : INode {
    override fun toString() = "LoopNode{init=$init, condition=$condition, step=$step, body=$body}"
}