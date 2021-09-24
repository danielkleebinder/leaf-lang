package org.leaflang.interpreter.visitor

import org.leaflang.error.ErrorCode
import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.memory.cell.IMemoryCell
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.interpreter.result.dataResult
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.parser.ast.INode
import org.leaflang.parser.ast.UnaryOperationNode

/**
 * Interprets the unary operation node.
 */
class UnaryOperationVisitor : IVisitor {

    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val unaryOperationNode = node as UnaryOperationNode
        val value = interpreter.interpret(unaryOperationNode.node).data
        val op = unaryOperationNode.op

        if (value is IMemoryCell) {
            return dataResult(value.unary(op))
        }

        // ASSERT: This should never happen because static semantic analysis must catch this error
        interpreter.abort(node, ErrorCode.UNSUPPORTED_OPERATION, "Given value " + value + " does not support unary operation " + unaryOperationNode.op)
        return emptyResult()
    }
}