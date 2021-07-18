package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.NumberNode

/**
 * Interprets a number node.
 */
class NumberVisitor : IVisitor {
    override fun matches(node: INode) = NumberNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode) = (node as NumberNode).value
}