package org.nyxlang.interpreter.value

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * String values are used to perform certain operations and
 * enable type coercion.
 */
class StringValue(override val value: String) : IValue {

    override fun unary(op: UnaryOperation) = throw UnknownOperationException("Unary operations are not supported for strings")

    override fun binary(right: IValue, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> stringValue(stringify() + right.stringify())
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        BinaryOperation.LESS_THAN -> binaryLessThan(right)
        BinaryOperation.LESS_THAN_OR_EQUAL -> binaryLessThanOrEqual(right)
        BinaryOperation.GREATER_THAN -> binaryGreaterThan(right)
        BinaryOperation.GREATER_THAN_OR_EQUAL -> binaryGreaterThanOrEqual(right)
        else -> throw UnknownOperationException("The operation $op is not supported for data type bool")
    }

    /**
     * Performs the '==' operation.
     */
    private fun binaryEqual(right: IValue) = when (right) {
        is StringValue -> boolValue(value == right.value)
        else -> throw UnknownOperationException("The == operation in number is not supported for $right")
    }

    /**
     * Performs the '!=' operation.
     */
    private fun binaryNotEqual(right: IValue) = when (right) {
        is StringValue -> boolValue(value != right.value)
        else -> throw UnknownOperationException("The != operation in number is not supported for $right")
    }

    /**
     * Performs the '<' operation.
     */
    private fun binaryLessThan(right: IValue) = when (right) {
        is StringValue -> boolValue(value < right.value)
        else -> throw UnknownOperationException("The < operation in number is not supported for $right")
    }

    /**
     * Performs the '<=' operation.
     */
    private fun binaryLessThanOrEqual(right: IValue) = when (right) {
        is StringValue -> boolValue(value <= right.value)
        else -> throw UnknownOperationException("The <= operation in number is not supported for $right")
    }

    /**
     * Performs the '>' operation.
     */
    private fun binaryGreaterThan(right: IValue) = when (right) {
        is StringValue -> boolValue(value > right.value)
        else -> throw UnknownOperationException("The > operation in number is not supported for $right")
    }

    /**
     * Performs the '>=' operation.
     */
    private fun binaryGreaterThanOrEqual(right: IValue) = when (right) {
        is StringValue -> boolValue(value >= right.value)
        else -> throw UnknownOperationException("The >= operation in number is not supported for $right")
    }

    override fun stringify() = value
    override fun toString() = "StringValue(value=$value)"
}