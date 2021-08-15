package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.numberResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.NumberNode

/**
 * Interprets a number node.
 */
class NumberVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = numberResult((node as NumberNode).value)
}