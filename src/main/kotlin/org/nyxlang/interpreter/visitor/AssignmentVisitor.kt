package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.parser.ast.AssignmentNode
import org.nyxlang.parser.ast.INode

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

        access?.assign(assignmentValue)
        return emptyResult()
    }
}