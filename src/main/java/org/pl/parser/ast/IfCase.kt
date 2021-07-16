package org.pl.parser.ast

/**
 * A single if case.
 */
class IfCase(val condition: INode?, val body: INode?) {
    override fun toString() = "IfCase{condition=$condition, body=$body}"
}