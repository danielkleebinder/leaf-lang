package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.parser.ast.INode

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