package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.boolResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.value.BoolNode

/**
 * Interprets the bool node.
 */
class BoolVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = boolResult((node as BoolNode).value)
}