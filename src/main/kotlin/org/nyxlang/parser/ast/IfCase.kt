package org.nyxlang.parser.ast

/**
 * A single if case.
 */
class IfCase(val condition: INode?, val body: INode?) {
    override fun toString() = "ConditionalCase{condition=$condition, body=$body}"
}