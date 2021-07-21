package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.continueResult
import org.nyxlang.parser.ast.INode

/**
 * Interprets a continue statement.
 */
class ContinueVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = continueResult()
}