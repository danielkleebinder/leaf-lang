package org.pl.parser.ast

import java.math.BigDecimal

/**
 * Single float value.
 */
class NumberNode(val value: BigDecimal) : INode {
    override fun toString() = "NumberNode{val=$value}"
}