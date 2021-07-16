package org.pl.parser.ast

/**
 * Loops contain a conditional statement and a loop body.
 */
class LoopNode(val condition: INode?, val body: INode?) : INode {
    override fun toString() = "LoopNode{condition=$condition, body=$body}"
}