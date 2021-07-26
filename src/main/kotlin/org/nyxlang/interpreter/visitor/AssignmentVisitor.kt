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
        val name = assignmentNode.name
        val offsetExpr = assignmentNode.offsetExpr
        val assignmentExpr = assignmentNode.assignmentExpr
        val activationRecord = interpreter.activationRecord!!

        val assignmentValue = interpreter.interpret(assignmentExpr).data
        if (offsetExpr != null) {
            val offset = interpreter.interpret(offsetExpr).data
                    ?: throw VisitorException("Provided get offset $offsetExpr is invalid")
            val storedValue = activationRecord[name]
                    ?: throw VisitorException("Variable with name \"$name\" does not exist")
            storedValue.set(offset, assignmentValue!!)
        } else {
            activationRecord[name] = assignmentValue
        }

        return emptyResult()
    }
}