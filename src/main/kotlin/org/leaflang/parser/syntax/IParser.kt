package org.leaflang.parser.syntax

import org.leaflang.parser.ast.INode


/**
 * An evaluator evaluates the next token(s) and returns a corresponding node.
 */
interface IParser {

    /**
     * Evaluates the next tokens.
     */
    fun parse(): INode
}