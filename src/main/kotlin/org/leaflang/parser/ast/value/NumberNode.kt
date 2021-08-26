package org.leaflang.parser.ast.value

import org.leaflang.parser.ast.INode
import org.leaflang.parser.utils.NodePosition
import java.math.BigDecimal

/**
 * Single float value.
 */
data class NumberNode(override val position: NodePosition,
                      val value: BigDecimal) : INode