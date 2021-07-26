package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.AssignmentNode

/**
 * Interprets the var assign node.
 */
class AssignmentVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val assignmentNode = node as AssignmentNode
        val varName = assignmentNode.name
        val varAssignment = assignmentNode.assignmentExpr
        val activationRecord = interpreter.activationRecord!!
        activationRecord[varName] = interpreter.interpret(varAssignment).data
        return emptyResult()
    }
}