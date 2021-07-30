package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.interpreter.result.objectResult
import org.nyxlang.interpreter.value.ObjectValue
import org.nyxlang.parser.ast.INode
import org.nyxlang.parser.ast.TypeInstantiationNode

/**
 * Interprets the custom type instantiation node.
 */
class TypeInstantiationVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val typeInstNode = node as TypeInstantiationNode
        val typeSpec = typeInstNode.spec!!
        val fields = typeInstNode.args
                .mapIndexed { index, arg ->
                    val paramName = arg.name ?: typeSpec.fields[index].name
                    val argumentValue = interpreter.interpret(arg.valueExpr).data!!
                    Pair(paramName, argumentValue)
                }.toMap()
        return objectResult(fields.toMutableMap())
    }
}