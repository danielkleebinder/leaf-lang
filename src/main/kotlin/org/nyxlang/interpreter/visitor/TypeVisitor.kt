package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.parser.ast.INode

/**
 * Interprets the type node.
 */
class TypeVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = emptyResult()
}