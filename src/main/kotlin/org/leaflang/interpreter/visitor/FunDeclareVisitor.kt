package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.exception.VisitorException
import org.leaflang.interpreter.memory.cell.TraitMemoryCell
import org.leaflang.interpreter.memory.cell.TypeMemoryCell
import org.leaflang.interpreter.result.DataRuntimeResult
import org.leaflang.interpreter.result.funResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.`fun`.FunDeclareNode

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

            // Currently only supported for custom types and traits
            if (typeCell is TypeMemoryCell) typeCell.members[funName] = result.data
            if (typeCell is TraitMemoryCell) typeCell.members[funName] = result.data
        }

        // Store the function as local variable for later
        if (funDeclareNode.extensionOf.isEmpty()) {
            activationRecord.define(funName, result.data)
        }

        // Return the function data result
        return result
    }
}