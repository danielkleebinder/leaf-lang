package org.leaflang.interpreter.visitor

import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.exception.VisitorException
import org.leaflang.interpreter.result.DataRuntimeResult
import org.leaflang.interpreter.result.dataResult
import org.leaflang.interpreter.memory.cell.IMemoryCell
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.UnaryOperationNode

/**
 * Interprets the unary operation node.
 */
class UnaryOperationVisitor : IVisitor {

    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val unaryOperationNode = node as UnaryOperationNode
        val value = interpreter.interpret(unaryOperationNode.node).data
        val op = unaryOperationNode.op

        if (value is IMemoryCell) {
            return dataResult(value.unary(op))
        }

        throw VisitorException("Given value " + value + " does not support unary operation " + unaryOperationNode.op)
    }
}