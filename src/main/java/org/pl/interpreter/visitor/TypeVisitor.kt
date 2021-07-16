package org.pl.interpreter.visitor

import org.pl.interpreter.IInterpreter
import org.pl.parser.ast.INode
import org.pl.parser.ast.TypeNode

/**
 * Interprets the type node.
 */
class TypeVisitor : IVisitor {
    override fun matches(node: INode) = TypeNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode) = (node as TypeNode).type
}