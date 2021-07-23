package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.interpreter.value.StringValue
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.StringNode

/**
 * Interprets the string node.
 */
class StringVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode) = dataResult(StringValue((node as StringNode).value))
}