package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.VarAccessNode

/**
 * Interprets the var access node.
 */
class VarAccessVisitor : IVisitor {
    override fun matches(node: INode) = VarAccessNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): Any? {
        val varAccessNode = node as VarAccessNode
        val varName = varAccessNode.identifier
        return interpreter.activationRecord!![varName]
                ?: throw VisitorException("Variable with name $varName undefined")
    }
}