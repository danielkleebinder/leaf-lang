package org.leaflang.parser.ast

import java.math.BigDecimal

/**
 * Single float value.
 */
data class NumberNode(val value: BigDecimal) : INode