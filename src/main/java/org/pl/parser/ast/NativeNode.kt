package org.pl.parser.ast

/**
 * A native node that contains a native programming language which should be executed.
 */
class NativeNode(val programCode: String) : INode {
    override fun toString() = "NativeNode{code=$programCode}"
}