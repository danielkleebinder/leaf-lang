package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.memory.cell.traitMemoryCell
import org.leaflang.interpreter.result.EmptyRuntimeResult
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.type.TraitDeclareNode

/**
 * Interprets a trait declaration node.
 */
class TraitDeclareVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): EmptyRuntimeResult {
        val traitNode = node as TraitDeclareNode
        val traitName = traitNode.name
        val traitCell = traitMemoryCell()

        // Define the trait in the current activation record
        val activationRecord = interpreter.activationRecord!!
        activationRecord.define(traitName, traitCell)

        return emptyResult()
    }
}