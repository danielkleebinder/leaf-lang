package org.nyxlang.interpreter.visitor

import org.nyxlang.interpreter.IInterpreter
import org.nyxlang.interpreter.exception.VisitorException
import org.nyxlang.interpreter.result.DataRuntimeResult
import org.nyxlang.interpreter.result.dataResult
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.BinaryOperationNode
import org.nyxlang.parser.ast.INode
import java.math.BigDecimal
import kotlin.math.pow

/**
 * Interprets the binary operation node.
 */
class BinaryOperationVisitor : IVisitor {

    override fun matches(node: INode) = BinaryOperationNode::class == node::class

    override fun visit(interpreter: IInterpreter, node: INode): DataRuntimeResult {
        val binaryOperationNode = node as BinaryOperationNode
        val left = interpreter.evalNode(binaryOperationNode.leftNode).data
        val right = interpreter.evalNode(binaryOperationNode.rightNode).data
        val op = binaryOperationNode.op

        if (left is BigDecimal && right is BigDecimal) {
            return dataResult(interpretNumbers(left, right, op))
        } else if (left is Boolean && right is Boolean) {
            return dataResult(interpretBools(left, right, op))
        }

        throw VisitorException("Given value $left is not compatible with $right")
    }

    /**
     * Performs arithmetic operations on the [left] and [right] operands using the given [op].
     */
    private fun interpretNumbers(left: BigDecimal, right: BigDecimal, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> left.add(right)
        BinaryOperation.MINUS -> left.subtract(right)
        BinaryOperation.DIVIDE -> left.divide(right)
        BinaryOperation.MULTIPLY -> left.multiply(right)
        BinaryOperation.POWER -> BigDecimal.valueOf(left.toDouble().pow(right.toDouble()))
        BinaryOperation.MOD -> left.remainder(right)
        BinaryOperation.EQUAL -> left == right
        BinaryOperation.NOT_EQUAL -> left != right
        BinaryOperation.LESS_THAN -> left < right
        BinaryOperation.LESS_THAN_OR_EQUAL -> left <= right
        BinaryOperation.GREATER_THAN -> left > right
        BinaryOperation.GREATER_THAN_OR_EQUAL -> left >= right
        else -> throw VisitorException("The operation $op is not supported for data type number")

    }

    /**
     * Performs logical operations on the [left] and [right] operands using the given [op].
     */
    private fun interpretBools(left: Boolean, right: Boolean, op: BinaryOperation) = when (op) {
        BinaryOperation.EQUAL -> left == right
        BinaryOperation.NOT_EQUAL -> left != right
        BinaryOperation.LOGICAL_AND -> left && right
        BinaryOperation.LOGICAL_OR -> left || right
        else -> throw VisitorException("The operation $op is not supported for data type bool")
    }
}