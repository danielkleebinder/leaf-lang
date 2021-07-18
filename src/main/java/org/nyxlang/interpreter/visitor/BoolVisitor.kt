package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.parser.ast.BoolNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets the bool node.
 */
class BoolVisitor : IVisitor {
    override fun matches(node: INode) = BoolNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode) = (node as BoolNode).value
}