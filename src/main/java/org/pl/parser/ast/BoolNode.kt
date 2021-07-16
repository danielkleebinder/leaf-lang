package org.pl.parser.ast

/**
 * Single boolean value.
 */
class BoolNode(val value: Boolean) : INode {
    override fun toString() = "BoolNode{val=$value}"
}