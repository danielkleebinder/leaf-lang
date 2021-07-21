package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.breakResult
import org.nyxlang.parser.ast.INode

/**
 * Interprets a break statement.
 */
class BreakVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = breakResult()
}