package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.result.DataRuntimeResult
import org.leaflang.interpreter.result.arrayResult
import org.leaflang.interpreter.memory.cell.IMemoryCell
import org.leaflang.parser.ast.value.ArrayNode
import org.leaflang.parser.ast.INode

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