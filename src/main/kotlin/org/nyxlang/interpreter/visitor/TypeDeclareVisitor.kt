package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.memory.cell.TypeMemoryCell
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.typeResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeDeclareNode

/**
 * Interprets a custom type declaration node.
 */
class TypeDeclareVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val typeDeclareNode = node as TypeDeclareNode
        val typeName = typeDeclareNode.name
        val typeSpec = typeDeclareNode.spec
        val result = typeResult(typeSpec!!)
        val typeCell = result.data as TypeMemoryCell

        // Add all fields with default values to the type memory cell
        typeDeclareNode.fields
                .flatMap { field -> field.declarations }
                .forEach {
                    val name = it.identifier
                    val value = interpreter.interpret(it.assignmentExpr)
                    if (value.hasData()) typeCell.members[name] = value.data!!
                }

        // Define the type in the current activation record
        val activationRecord = interpreter.activationRecord!!
        activationRecord.define(typeName, result.data)

        return result
    }
}