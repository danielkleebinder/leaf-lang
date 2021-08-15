package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.parser.ast.INode

/**
 * Interprets and runs a given node.
 */
interface IVisitor {

    /**
     * Interprets and runs a given [node]. The given [interpreter] instance can be
     * used to recursively traverse the syntax tree.
     */
    fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult
}