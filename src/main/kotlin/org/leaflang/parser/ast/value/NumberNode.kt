package org.leaflang.parser.ast.value

import org.leaflang.parser.ast.INode
import java.math.BigDecimal

/**
 * Single float value.
 */
data class NumberNode(val value: BigDecimal) : INode