package org.leaflang.parser.utils

/**
 * Node position inside the program text.
 */
data class NodePosition(val row: Int,
                        val column: Int,
                        val position: Int)