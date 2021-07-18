package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.parser.ast.FunCallNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets a function call node.
 */
class FunCallVisitor : IVisitor {
    override fun matches(node: INode) = FunCallNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode) {}
}