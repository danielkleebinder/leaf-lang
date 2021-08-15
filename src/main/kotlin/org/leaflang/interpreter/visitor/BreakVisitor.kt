package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.breakResult
import org.leaflang.parser.ast.INode

/**
 * Interprets a break statement.
 */
class BreakVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = breakResult()
}