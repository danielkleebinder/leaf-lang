package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.arrayResult
import org.nyxlang.interpreter.value.IValue
import org.nyxlang.parser.ast.ArrayNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets the array node.
 */
class ArrayVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val arrayNode = node as ArrayNode
        val values = arrayListOf<IValue?>()
        for (element in arrayNode.elements) {
            values.add(interpreter.interpret(element).data)
        }
        return arrayResult(values.toTypedArray())
    }
}