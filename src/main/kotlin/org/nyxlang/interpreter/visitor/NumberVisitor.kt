package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.numberResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.NumberNode

/**
 * Interprets a number node.
 */
class NumberVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = numberResult((node as NumberNode).value)
}