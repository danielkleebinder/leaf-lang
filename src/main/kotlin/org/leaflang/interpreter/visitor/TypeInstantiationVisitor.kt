package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.memory.cell.IMemoryCell
import org.leaflang.interpreter.result.DataRuntimeResult
import org.leaflang.interpreter.result.objectResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.type.TypeInstantiationNode

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
        val fields = mutableMapOf<String, IMemoryCell>()

        // Init parameters should be interpreted and applied to the type instance
        typeSpec.fields
                .forEachIndexed { index, field ->
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
                        argumentValue = typeCell.members[field.name]?.copy()
                    }

                    if (argumentValue != null) {
                        fields[field.name] = argumentValue
                    }
                }

        // Add defined extension functions as member to this type instance
        typeSpec.functions
                .forEach { function ->
                    var funValue: IMemoryCell? = null

                    // Look if this function even has an implementation
                    if (typeCell != null) {
                        funValue = typeCell.members[function.name]?.copy()
                    }

                    if (funValue != null) {
                        fields[function.name] = funValue
                    }
                }

        return objectResult(fields)
    }
}