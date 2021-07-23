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

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.POSITIVE -> this
        UnaryOperation.NEGATE -> numberValue(value.unaryMinus())
        UnaryOperation.INCREMENT -> numberValue(value.inc())
        UnaryOperation.DECREMENT -> numberValue(value.dec())
        UnaryOperation.BIT_COMPLEMENT -> numberValue(BigDecimal.valueOf(value.toLong().inv()))
        else -> throw UnknownOperationException("The operation $op is not supported for data type number")
    }

    override fun binary(right: IValue, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> binaryPlus(right)
        BinaryOperation.MINUS -> binaryMinus(right)
        BinaryOperation.DIV -> binaryDiv(right)
        BinaryOperation.TIMES -> binaryTimes(right)
        BinaryOperation.REM -> binaryRem(right)
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        BinaryOperation.LESS_THAN -> binaryLessThan(right)
        BinaryOperation.LESS_THAN_OR_EQUAL -> binaryLessThanOrEqual(right)
        BinaryOperation.GREATER_THAN -> binaryGreaterThan(right)
        BinaryOperation.GREATER_THAN_OR_EQUAL -> binaryGreaterThanOrEqual(right)
        else -> throw UnknownOperationException("The operation $op is not supported for data type number")
    }

    /**
     * Performs the '+' operation.
     */
    private fun binaryPlus(right: IValue) = when (right) {
        is NumberValue -> numberValue(value + right.value)
        is StringValue -> stringValue(stringify() + right.value)
        else -> throw UnknownOperationException("The plus operation in number is not supported for $right")
    }

    /**
     * Performs the '-' operation.
     */
    private fun binaryMinus(right: IValue) = when (right) {
        is NumberValue -> numberValue(value - right.value)
        else -> throw UnknownOperationException("The minus operation in number is not supported for $right")
    }

    /**
     * Performs the '/' operation.
     */
    private fun binaryDiv(right: IValue) = when (right) {
        is NumberValue -> numberValue(value / right.value)
        else -> throw UnknownOperationException("The divide operation in number is not supported for $right")
    }

    /**
     * Performs the '*' operation.
     */
    private fun binaryTimes(right: IValue) = when (right) {
        is NumberValue -> numberValue(value * right.value)
        else -> throw UnknownOperationException("The multiply operation in number is not supported for $right")
    }

    /**
     * Performs the '%' operation.
     */
    private fun binaryRem(right: IValue) = when (right) {
        is NumberValue -> numberValue(value % right.value)
        else -> throw UnknownOperationException("The remainder operation in number is not supported for $right")
    }

    /**
     * Performs the '==' operation.
     */
    private fun binaryEqual(right: IValue) = when (right) {
        is NumberValue -> boolValue(value == right.value)
        else -> throw UnknownOperationException("The remainder operation in number is not supported for $right")
    }

    /**
     * Performs the '!=' operation.
     */
    private fun binaryNotEqual(right: IValue) = when (right) {
        is NumberValue -> boolValue(value != right.value)
        else -> throw UnknownOperationException("The != operation in number is not supported for $right")
    }

    /**
     * Performs the '<' operation.
     */
    private fun binaryLessThan(right: IValue) = when (right) {
        is NumberValue -> boolValue(value < right.value)
        else -> throw UnknownOperationException("The < operation in number is not supported for $right")
    }

    /**
     * Performs the '<=' operation.
     */
    private fun binaryLessThanOrEqual(right: IValue) = when (right) {
        is NumberValue -> boolValue(value <= right.value)
        else -> throw UnknownOperationException("The <= operation in number is not supported for $right")
    }

    /**
     * Performs the '>' operation.
     */
    private fun binaryGreaterThan(right: IValue) = when (right) {
        is NumberValue -> boolValue(value > right.value)
        else -> throw UnknownOperationException("The > operation in number is not supported for $right")
    }

    /**
     * Performs the '>=' operation.
     */
    private fun binaryGreaterThanOrEqual(right: IValue) = when (right) {
        is NumberValue -> boolValue(value >= right.value)
        else -> throw UnknownOperationException("The >= operation in number is not supported for $right")
    }

    override fun stringify() = value.toString()
    override fun toString() = "NumberValue(value=$value)"
}