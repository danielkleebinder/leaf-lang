package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.VarAccessNode

/**
 * Interprets the var access node.
 */
class VarAccessVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val varAccessNode = node as VarAccessNode
        val varName = varAccessNode.identifier
        val value = interpreter.activationRecord!![varName]
                ?: throw VisitorException("Variable with name $varName undefined")
        return dataResult(value)
    }
}