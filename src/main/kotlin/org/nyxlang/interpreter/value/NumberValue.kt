package org.nyxlang.interpreter.value

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation
import java.math.BigDecimal

/**
 * Number values are used to perform certain operations and
 * enable type coercion.
 */
class NumberValue(override val value: BigDecimal) : IValue {

    override fun binary(right: IValue, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> when (right) {
            is NumberValue -> numberValue(value + right.value)
            is StringValue -> stringValue(stringify() + right.value)
            else -> throw UnknownOperationException("The plus operation in number is not supported for $right")
        }
        BinaryOperation.MINUS -> when (right) {
            is NumberValue -> numberValue(value - right.value)
            else -> throw UnknownOperationException("The minus operation in number is not supported for $right")
        }
        BinaryOperation.DIVIDE -> when (right) {
            is NumberValue -> numberValue(value / right.value)
            else -> throw UnknownOperationException("The divide operation in number is not supported for $right")
        }
        BinaryOperation.MULTIPLY -> when (right) {
            is NumberValue -> numberValue(value * right.value)
            else -> throw UnknownOperationException("The multiply operation in number is not supported for $right")
        }
        BinaryOperation.REM -> when (right) {
            is NumberValue -> numberValue(value % right.value)
            else -> throw UnknownOperationException("The remainder operation in number is not supported for $right")
        }
        BinaryOperation.EQUAL -> when (right) {
            is NumberValue -> boolValue(value == right.value)
            else -> throw UnknownOperationException("The remainder operation in number is not supported for $right")
        }
        BinaryOperation.NOT_EQUAL -> when (right) {
            is NumberValue -> boolValue(value != right.value)
            else -> throw UnknownOperationException("The != operation in number is not supported for $right")
        }
        BinaryOperation.LESS_THAN -> when (right) {
            is NumberValue -> boolValue(value < right.value)
            else -> throw UnknownOperationException("The < operation in number is not supported for $right")
        }
        BinaryOperation.LESS_THAN_OR_EQUAL -> when (right) {
            is NumberValue -> boolValue(value <= right.value)
            else -> throw UnknownOperationException("The <= operation in number is not supported for $right")
        }
        BinaryOperation.GREATER_THAN -> when (right) {
            is NumberValue -> boolValue(value > right.value)
            else -> throw UnknownOperationException("The > operation in number is not supported for $right")
        }
        BinaryOperation.GREATER_THAN_OR_EQUAL -> when (right) {
            is NumberValue -> boolValue(value >= right.value)
            else -> throw UnknownOperationException("The >= operation in number is not supported for $right")
        }
        else -> throw UnknownOperationException("The operation $op is not supported for data type number")
    }

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.POSITIVE -> this
        UnaryOperation.NEGATE -> numberValue(value.unaryMinus())
        UnaryOperation.INCREMENT -> numberValue(value.inc())
        UnaryOperation.DECREMENT -> numberValue(value.dec())
        UnaryOperation.BIT_COMPLEMENT -> numberValue(BigDecimal.valueOf(value.toLong().inv()))
        else -> throw UnknownOperationException("The operation $op is not supported for data type number")
    }

    override fun stringify() = value.toString()
    override fun toString() = "NumberValue(value=$value)"
}

/**
 * Creates a number [value].
 */
fun numberValue(value: BigDecimal) = NumberValue(value)