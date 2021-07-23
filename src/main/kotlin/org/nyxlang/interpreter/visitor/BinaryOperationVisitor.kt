package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.interpreter.value.IValue
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode

/**
 * Interprets the binary operation node.
 */
class BinaryOperationVisitor : IVisitor {

    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val binaryOperationNode = node as BinaryOperationNode
        val left = interpreter.interpret(binaryOperationNode.leftNode).data
        val right = interpreter.interpret(binaryOperationNode.rightNode).data
        val op = binaryOperationNode.op

        if (left is IValue && right is IValue) {
            return dataResult(left.binary(right, op))
        }

        throw VisitorException("Given value $left is not compatible with $right")
    }
}