package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.interpreter.value.BoolValue
import org.nyxlang.parser.ast.BoolNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets the bool node.
 */
class BoolVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = dataResult(BoolValue((node as BoolNode).value))
}