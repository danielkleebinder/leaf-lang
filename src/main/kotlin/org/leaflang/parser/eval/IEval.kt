package org.leaflang.parser.eval

import org.leaflang.parser.ast.INode


/**
 * An evaluator evaluates the next token(s) and returns a corresponding node.
 */
interface IEval {

    /**
     * Evaluates the next tokens.
     */
    fun eval(): INode
}