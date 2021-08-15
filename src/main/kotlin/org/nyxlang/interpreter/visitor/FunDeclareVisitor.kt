package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.memory.cell.TypeMemoryCell
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.funResult
import org.nyxlang.parser.ast.FunDeclareNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets a function declaration node.
 */
class FunDeclareVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val funDeclareNode = node as FunDeclareNode
        val funName = funDeclareNode.name
        val result = funResult(funDeclareNode.spec!!)
        val activationRecord = interpreter.activationRecord!!

        if (funName == null) return result

        // Iterate all extensions and add this function to the all the types
        funDeclareNode.extensionOf.forEach {
            val typeCell = activationRecord[it.type]
                    ?: throw VisitorException("Type \"${it.type}\" not available for extension")

            // Currently only supported for custom types
            if (typeCell is TypeMemoryCell) {
                typeCell.members[funName] = result.data
            }
        }

        // Store the function as local variable for later
        if (funDeclareNode.extensionOf.isEmpty()) {
            activationRecord.define(funName, result.data)
        }

        // Return the function data result
        return result
    }
}