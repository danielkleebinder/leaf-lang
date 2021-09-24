package org.leaflang.interpreter.visitor

import org.leaflang.error.ErrorCode
import org.leaflang.interpreter.IInterpreter
import org.leaflang.interpreter.memory.cell.IMemoryCell
import org.leaflang.interpreter.result.IRuntimeResult
import org.leaflang.interpreter.result.dataResult
import org.leaflang.interpreter.result.emptyResult
import org.leaflang.parser.ast.BinaryOperationNode
import org.leaflang.parser.ast.INode

/**
 * Interprets the binary operation node.
 */
class BinaryOperationVisitor : IVisitor {

    override fun visit(interpreter: IInterpreter, node: INode): IRuntimeResult {
        val binaryOperationNode = node as BinaryOperationNode
        val left = interpreter.interpret(binaryOperationNode.leftNode).data
        val right = interpreter.interpret(binaryOperationNode.rightNode).data
        val op = binaryOperationNode.op

        if (left is IMemoryCell && right is IMemoryCell) {
            return dataResult(left.binary(right, op))
        }

        // ASSERT: This should never happen because static semantic analysis must catch this error
        interpreter.abort(node, ErrorCode.UNSUPPORTED_OPERATION, "Given value ${binaryOperationNode.leftNode} is not compatible with ${binaryOperationNode.rightNode}")
        return emptyResult()
    }
}