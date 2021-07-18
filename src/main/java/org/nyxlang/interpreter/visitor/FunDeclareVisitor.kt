package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.withStaticScope
import org.nyxlang.parser.ast.FunDeclareNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets a function declaration node.
 */
class FunDeclareVisitor : IVisitor {
    override fun matches(node: INode) = FunDeclareNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode) {
        val funDeclareNode = node as FunDeclareNode
        interpreter.withStaticScope(funDeclareNode.name) {  }
    }
}