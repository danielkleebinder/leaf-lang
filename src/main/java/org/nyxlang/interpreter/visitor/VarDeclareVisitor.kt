package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.VarDeclareNode

/**
 * Interprets the var declaration node.
 */
class VarDeclareVisitor : IVisitor {
    override fun matches(node: INode) = VarDeclareNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): String? {
        val varDeclarationNode = node as VarDeclareNode
        for (declaration in varDeclarationNode.declarations) {
            val name = declaration.identifier
            val type = interpreter.evalNode(declaration.typeExpr)
            val value = interpreter.evalNode(declaration.assignmentExpr)
            interpreter.activationRecord!![name] = value
        }
        return "<var create>"
    }
}