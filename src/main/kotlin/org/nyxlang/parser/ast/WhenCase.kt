package org.nyxlang.parser.ast

/**
 * A single when case.
 */
class WhenCase(val matches: INode, val body: INode) {
    override fun toString() = "WhenCase{matches=$matches, body=$body}"
}