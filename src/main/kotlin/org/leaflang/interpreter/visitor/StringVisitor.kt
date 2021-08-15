package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.stringResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.StringNode

/**
 * Interprets the string node.
 */
class StringVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = stringResult((node as StringNode).value)
}