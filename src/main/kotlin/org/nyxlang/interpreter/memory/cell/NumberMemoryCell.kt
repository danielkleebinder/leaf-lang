package org.nyxlang.interpreter.memory.cell

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation
import java.math.BigDecimal

/**
 * Number values are used to perform certain operations and
 * enable type coercion.
 */
class NumberMemoryCell(private var data: BigDecimal,
                       override val members: Map<String, IMemoryCell> = mapOf()) : IMemoryCell {

    override val value: BigDecimal
        get() = data

    override fun assign(newValue: IMemoryCell) {
        if (newValue !is NumberMemoryCell) throw UnknownOperationException("Cannot assign \"$newValue\" to number")
        data = newValue.value
    }

    override fun copy() = numberMemoryCell(data)
    override fun get(index: IMemoryCell) = throw UnknownOperationException("Numbers do not support index based access")

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.POSITIVE -> this
        UnaryOperation.NEGATE -> numberMemoryCell(data.unaryMinus())
        UnaryOperation.INCREMENT -> numberMemoryCell(data.inc())
        UnaryOperation.DECREMENT -> numberMemoryCell(data.dec())
        UnaryOperation.BIT_COMPLEMENT -> numberMemoryCell(BigDecimal.valueOf(data.toLong().inv()))
        else -> throw UnknownOperationException("The operation $op is not supported for data type number")
    }

    override fun binary(right: IMemoryCell, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> binaryPlus(right)
        BinaryOperation.MINUS -> binaryMinus(right)
        BinaryOperation.DIV -> binaryDiv(right)
        BinaryOperation.TIMES -> binaryTimes(right)
        BinaryOperation.REM -> binaryRem(right)
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        BinaryOperation.LESS -> binaryLessThan(right)
        BinaryOperation.LESS_EQUALS -> binaryLessThanOrEqual(right)
        BinaryOperation.GREATER -> binaryGreaterThan(right)
        BinaryOperation.GREATER_EQUALS -> binaryGreaterThanOrEqual(right)
        else -> throw UnknownOperationException("The operation $op is not supported for data type number")
    }

    /**
     * Performs the '+' operation.
     */
    private fun binaryPlus(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> numberMemoryCell(data + right.data)
        is StringMemoryCell -> stringMemoryCell(stringify() + right.value)
        is ArrayMemoryCell -> arrayMemoryCell(arrayOf(this, *right.value))
        else -> throw UnknownOperationException("The plus operation in number is not supported for $right")
    }

    /**
     * Performs the '-' operation.
     */
    private fun binaryMinus(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> numberMemoryCell(data - right.data)
        else -> throw UnknownOperationException("The minus operation in number is not supported for $right")
    }

    /**
     * Performs the '/' operation.
     */
    private fun binaryDiv(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> numberMemoryCell(data / right.data)
        else -> throw UnknownOperationException("The divide operation in number is not supported for $right")
    }

    /**
     * Performs the '*' operation.
     */
    private fun binaryTimes(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> numberMemoryCell(data * right.data)
        else -> throw UnknownOperationException("The multiply operation in number is not supported for $right")
    }

    /**
     * Performs the '%' operation.
     */
    private fun binaryRem(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> numberMemoryCell(data % right.data)
        else -> throw UnknownOperationException("The remainder operation in number is not supported for $right")
    }

    /**
     * Performs the '==' operation.
     */
    private fun binaryEqual(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> boolMemoryCell(data == right.data)
        else -> throw UnknownOperationException("The remainder operation in number is not supported for $right")
    }

    /**
     * Performs the '!=' operation.
     */
    private fun binaryNotEqual(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> boolMemoryCell(data != right.data)
        else -> throw UnknownOperationException("The != operation in number is not supported for $right")
    }

    /**
     * Performs the '<' operation.
     */
    private fun binaryLessThan(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> boolMemoryCell(data < right.data)
        else -> throw UnknownOperationException("The < operation in number is not supported for $right")
    }

    /**
     * Performs the '<=' operation.
     */
    private fun binaryLessThanOrEqual(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> boolMemoryCell(data <= right.data)
        else -> throw UnknownOperationException("The <= operation in number is not supported for $right")
    }

    /**
     * Performs the '>' operation.
     */
    private fun binaryGreaterThan(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> boolMemoryCell(data > right.data)
        else -> throw UnknownOperationException("The > operation in number is not supported for $right")
    }

    /**
     * Performs the '>=' operation.
     */
    private fun binaryGreaterThanOrEqual(right: IMemoryCell) = when (right) {
        is NumberMemoryCell -> boolMemoryCell(data >= right.data)
        else -> throw UnknownOperationException("The >= operation in number is not supported for $right")
    }

    override fun stringify() = data.toString()
    override fun toString() = "NumberValue(value=$data)"
}