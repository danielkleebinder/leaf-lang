package org.nyxlang.interpreter.value

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation

/**
 * String values are used to perform certain operations and
 * enable type coercion.
 */
class StringValue(override val value: String) : IValue {

    override fun binary(right: IValue, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> stringValue(stringify() + right.stringify())
        BinaryOperation.EQUAL -> when (right) {
            is StringValue -> boolValue(value == right.value)
            else -> throw UnknownOperationException("The == operation in number is not supported for $right")
        }
        BinaryOperation.NOT_EQUAL -> when (right) {
            is StringValue -> boolValue(value != right.value)
            else -> throw UnknownOperationException("The != operation in number is not supported for $right")
        }
        BinaryOperation.LESS_THAN -> when (right) {
            is StringValue -> boolValue(value < right.value)
            else -> throw UnknownOperationException("The < operation in number is not supported for $right")
        }
        BinaryOperation.LESS_THAN_OR_EQUAL -> when (right) {
            is StringValue -> boolValue(value <= right.value)
            else -> throw UnknownOperationException("The <= operation in number is not supported for $right")
        }
        BinaryOperation.GREATER_THAN -> when (right) {
            is StringValue -> boolValue(value > right.value)
            else -> throw UnknownOperationException("The > operation in number is not supported for $right")
        }
        BinaryOperation.GREATER_THAN_OR_EQUAL -> when (right) {
            is StringValue -> boolValue(value >= right.value)
            else -> throw UnknownOperationException("The >= operation in number is not supported for $right")
        }
        else -> throw UnknownOperationException("The operation $op is not supported for data type bool")
    }

    override fun unary(op: UnaryOperation) = throw UnknownOperationException("Unary operations are not supported for strings")
    override fun stringify() = value
    override fun toString() = "StringValue(value=$value)"
}

/**
 * Creates a string [value].
 */
fun stringValue(value: String) = StringValue(value)