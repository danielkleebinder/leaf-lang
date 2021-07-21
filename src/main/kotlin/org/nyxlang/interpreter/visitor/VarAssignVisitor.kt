package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.IRuntimeResult
import org.nyxlang.interpreter.result.emptyResult
import org.nyxlang.interpreter.result.unpack
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.VarAssignNode

/**
 * Interprets the var assign node.
 */
class VarAssignVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val varAssignNode = node as VarAssignNode
        val varName = varAssignNode.identifier
        val varAssignment = varAssignNode.assignmentExpr
        val activationRecord = interpreter.activationRecord!!
        activationRecord[varName] = interpreter.interpret(varAssignment).unpack()
        return emptyResult()
    }
}