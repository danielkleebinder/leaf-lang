package org.nyxlang.interpreter.memory.cell

import org.nyxlang.interpreter.exception.UnknownOperationException
import org.nyxlang.parser.ast.BinaryOperation
import org.nyxlang.parser.ast.UnaryOperation
import java.math.BigDecimal

/**
 * String values are used to perform certain operations and
 * enable type coercion.
 */
class StringMemoryCell(private var data: String,
                       override val members: Map<String, IMemoryCell> = mapOf()) : IMemoryCell {

    override val value: String
        get() = data

    override fun assign(newValue: IMemoryCell) {
        if (newValue !is StringMemoryCell) throw UnknownOperationException("Cannot assign \"$newValue\" to string")
        data = newValue.value
    }

    override fun copy() = stringMemoryCell(data)

    override fun get(index: IMemoryCell) = when (index) {
        is NumberMemoryCell -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= data.length) throw UnknownOperationException("String index $indexAsInt out of bounds")
            stringMemoryCell(data[indexAsInt].toString())
        }
        else -> throw UnknownOperationException("The index based get operation in string is not supported for $index")
    }

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.BIT_COMPLEMENT -> NumberMemoryCell(BigDecimal.valueOf(data.length.toLong()))
        else -> throw UnknownOperationException("The operation $op is not supported for string data type")
    }

    override fun binary(right: IMemoryCell, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> binaryPlus(right)
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        BinaryOperation.LESS -> binaryLessThan(right)
        BinaryOperation.LESS_EQUALS -> binaryLessThanOrEqual(right)
        BinaryOperation.GREATER -> binaryGreaterThan(right)
        BinaryOperation.GREATER_EQUALS -> binaryGreaterThanOrEqual(right)
        else -> throw UnknownOperationException("The operation $op is not supported for data type string")
    }

    /**
     * Performs the '+' operation.
     */
    private fun binaryPlus(right: IMemoryCell) = stringMemoryCell(stringify() + right.stringify())

    /**
     * Performs the '==' operation.
     */
    private fun binaryEqual(right: IMemoryCell) = when (right) {
        is StringMemoryCell -> boolMemoryCell(data == right.data)
        else -> throw UnknownOperationException("The == operation in number is not supported for $right")
    }

    /**
     * Performs the '!=' operation.
     */
    private fun binaryNotEqual(right: IMemoryCell) = when (right) {
        is StringMemoryCell -> boolMemoryCell(data != right.data)
        else -> throw UnknownOperationException("The != operation in number is not supported for $right")
    }

    /**
     * Performs the '<' operation.
     */
    private fun binaryLessThan(right: IMemoryCell) = when (right) {
        is StringMemoryCell -> boolMemoryCell(data < right.data)
        else -> throw UnknownOperationException("The < operation in number is not supported for $right")
    }

    /**
     * Performs the '<=' operation.
     */
    private fun binaryLessThanOrEqual(right: IMemoryCell) = when (right) {
        is StringMemoryCell -> boolMemoryCell(data <= right.data)
        else -> throw UnknownOperationException("The <= operation in number is not supported for $right")
    }

    /**
     * Performs the '>' operation.
     */
    private fun binaryGreaterThan(right: IMemoryCell) = when (right) {
        is StringMemoryCell -> boolMemoryCell(data > right.data)
        else -> throw UnknownOperationException("The > operation in number is not supported for $right")
    }

    /**
     * Performs the '>=' operation.
     */
    private fun binaryGreaterThanOrEqual(right: IMemoryCell) = when (right) {
        is StringMemoryCell -> boolMemoryCell(data >= right.data)
        else -> throw UnknownOperationException("The >= operation in number is not supported for $right")
    }

    override fun stringify() = data
    override fun toString() = "StringMemoryCell(value=$data)"
}