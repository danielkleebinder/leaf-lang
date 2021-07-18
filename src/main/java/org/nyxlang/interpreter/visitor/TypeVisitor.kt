package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeNode

/**
 * Interprets the type node.
 */
class TypeVisitor : IVisitor {
    override fun matches(node: INode) = TypeNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode) = (node as TypeNode).type
}