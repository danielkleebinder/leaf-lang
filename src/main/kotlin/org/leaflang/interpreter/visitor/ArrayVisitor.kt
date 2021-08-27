package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.memory.cell.IMemoryCell
import org.leaflang.interpreter.result.DataRuntimeResult
import org.leaflang.interpreter.result.arrayResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.value.ArrayNode

/**
 * Interprets the array node.
 */
class ArrayVisitor : IVisitor {
    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val arrayNode = node as ArrayNode
        val values = arrayListOf<IMemoryCell?>()
        for (element in arrayNode.elements) {
            values.add(interpreter.interpret(element).data)
        }
        return arrayResult(values.toTypedArray())
    }
}