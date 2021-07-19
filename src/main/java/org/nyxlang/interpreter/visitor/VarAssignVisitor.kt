package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.VarAssignNode

/**
 * Interprets the var assign node.
 */
class VarAssignVisitor : IVisitor {
    override fun matches(node: INode) = VarAssignNode::class == node::class
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val varAssignNode = node as VarAssignNode
        val varName = varAssignNode.identifier
        val varAssignment = varAssignNode.assignmentExpr
        interpreter.activationRecord!![varName] = interpreter.evalNode(varAssignment).data
        return emptyResult()
    }
}