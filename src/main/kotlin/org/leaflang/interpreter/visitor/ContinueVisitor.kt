package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.continueResult
import org.leaflang.parser.ast.INode

/**
 * Interprets a continue statement.
 */
class ContinueVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = continueResult()
}