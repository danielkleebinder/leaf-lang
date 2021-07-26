package org.nyxlang.interpreter.value

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation
import java.math.BigDecimal

/**
 * String values are used to perform certain operations and
 * enable type coercion.
 */
class StringValue(override var value: String) : IValue {

    override fun set(index: IValue, newValue: IValue) = when (index) {
        is NumberValue -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= value.length) throw UnknownOperationException("String index $indexAsInt out of bounds")
            value = value.replaceRange(indexAsInt, indexAsInt + 1, newValue.stringify())
        }
        else -> throw UnknownOperationException("The index based set operation in string is not supported for $index")
    }

    override fun get(index: IValue) = when (index) {
        is NumberValue -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= value.length) throw UnknownOperationException("String index $indexAsInt out of bounds")
            stringValue(value[indexAsInt].toString())
        }
        else -> throw UnknownOperationException("The index based get operation in string is not supported for $index")
    }

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.BIT_COMPLEMENT -> NumberValue(BigDecimal.valueOf(value.length.toLong()))
        else -> throw UnknownOperationException("The operation $op is not supported for string data type")
    }

    override fun binary(right: IValue, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> binaryPlus(right)
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        BinaryOperation.LESS_THAN -> binaryLessThan(right)
        BinaryOperation.LESS_THAN_OR_EQUAL -> binaryLessThanOrEqual(right)
        BinaryOperation.GREATER_THAN -> binaryGreaterThan(right)
        BinaryOperation.GREATER_THAN_OR_EQUAL -> binaryGreaterThanOrEqual(right)
        else -> throw UnknownOperationException("The operation $op is not supported for data type bool")
    }

    /**
     * Performs the '+' operation.
     */
    private fun binaryPlus(right: IValue) = stringValue(stringify() + right.stringify())

    /**
     * Performs the '[..]' operations.
     */
    private fun binaryGet(right: IValue) = when (right) {
        is NumberValue -> {
            if (right.value.intValueExact() < 0 || right.value.intValueExact() >= value.length) {
                throw UnknownOperationException("String index ${right.value} out of bounds")
            }
            StringValue(value[right.value.intValueExact()].toString())
        }
        else -> throw UnknownOperationException("The [..] operation in array is not supported for $right")
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