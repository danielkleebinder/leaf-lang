package org.leaflang.parser.utils

import org.leaflang.parser.ILeafParser

/**
 * Concrete node factory implementation for a given source.
 */
class NodeFactory(private val parser: ILeafParser) : INodeFactory {
}