package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.parser.ast.INode

/**
 * Interprets and runs a given node.
 */
interface IVisitor {

    /**
     * Tests if this visitor is applicable for the given node.
     *
     * @param node Node to be run.
     * @return True if applicable, otherwise false.
     */
    fun matches(node: INode): Boolean

    /**
     * Interprets and runs a given [node]. The given [interpreter] instance can be
     * used to recursively traverse the syntax tree.
     */
    fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult
}