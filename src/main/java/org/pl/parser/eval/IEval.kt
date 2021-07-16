package org.pl.parser.eval

import org.pl.parser.ast.INode


/**
 * An evaluator evaluates the next token(s) and returns a corresponding node.
 */
interface IEval {

    /**
     * Evaluates the next tokens.
     */
    fun eval(): INode
}