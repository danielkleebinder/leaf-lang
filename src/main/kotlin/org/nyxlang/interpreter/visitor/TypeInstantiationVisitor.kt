package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.memory.cell.IMemoryCell
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.objectResult
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeInstantiationNode

/**
 * Interprets the custom type instantiation node.
 */
class TypeInstantiationVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val typeInstNode = node as TypeInstantiationNode
        val typeName = typeInstNode.name
        val typeSpec = typeInstNode.spec!!
        val typeArgs = typeInstNode.args

        val activationRecord = interpreter.activationRecord!!
        val typeCell = activationRecord[typeName]

        // Set parameters should be interpreted and applied to the type instance
        val fields = typeSpec.fields
                .mapIndexed { index, field ->
                    var argumentValue: IMemoryCell? = null

                    // Look for named arguments like: new Dog { name = "Bello" }
                    val named = typeArgs.find { it.name == field.name }
                    if (named != null) {
                        argumentValue = interpreter.interpret(named.valueExpr).data
                    }

                    // Look for unnamed arguments like: new Dog { "Bello" }
                    if (argumentValue == null && typeArgs.size > index) {
                        argumentValue = interpreter.interpret(typeArgs[index].valueExpr).data
                    }

                    // Look for already interpreted default values like: type Dog { name = "Bello" }
                    if (argumentValue == null && typeCell != null) {
                        argumentValue = typeCell.members[field.name]
                    }

                    Pair(field.name, argumentValue)
                }
                .filter { it.second != null }
                .map { Pair(it.first, it.second!!) }
                .toMap()
                .toMutableMap()

        return objectResult(fields)
    }
}