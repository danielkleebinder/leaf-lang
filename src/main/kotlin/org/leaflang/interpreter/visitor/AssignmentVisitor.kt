package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.exception.VisitorException
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.parser.ast.AssignmentNode
import org.leaflang.parser.ast.INode

/**
 * Interprets the var assign node.
 */
class AssignmentVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val assignmentNode = node as AssignmentNode
        val accessNode = assignmentNode.accessNode
        val assignmentExpr = assignmentNode.assignmentExpr

        val access = interpreter.interpret(accessNode).data
        val assignmentValue = interpreter.interpret(assignmentExpr).data
                ?: throw VisitorException("Assignment value for \"${accessNode.name}\" is undefined")

        if (access != null) {
            access.assign(assignmentValue)
        } else if (accessNode.children.isEmpty()) {
            interpreter.activationRecord!![accessNode.name] = assignmentValue
        }
        return emptyResult()
    }
}