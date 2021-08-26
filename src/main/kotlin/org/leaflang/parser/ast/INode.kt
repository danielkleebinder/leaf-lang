package org.leaflang.parser.ast

import org.leaflang.parser.utils.NodePosition

/**
 * All nodes inside an abstract syntax tree have to implement this interface.
 */
interface INode {

    /**
     * The position of this node in the program text.
     */
    val position: NodePosition
}