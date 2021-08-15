package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.parser.ast.INode

/**
 * Interprets the type node.
 */
class TypeVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = emptyResult()
}