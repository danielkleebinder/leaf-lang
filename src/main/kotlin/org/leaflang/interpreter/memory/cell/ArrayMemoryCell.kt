package org.leaflang.interpreter.memory.cell

import org.leaflang.interpreter.exception.UnknownOperationException
import org.leaflang.parser.ast.BinaryOperation
import org.leaflang.parser.ast.UnaryOperation
import java.math.BigDecimal

/**
 * Array values are used to perform certain operations and
 * enable type coercion.
 */
class ArrayMemoryCell(private var data: Array<IMemoryCell?>,
                      override val members: Map<String, IMemoryCell> = mapOf()) : IMemoryCell {

    override val value: Array<IMemoryCell?>
        get() = data

    override fun assign(newValue: IMemoryCell) {
        if (newValue !is ArrayMemoryCell) throw UnknownOperationException("Cannot assign \"$newValue\" to array")
        data = newValue.value
    }

    override fun copy() = arrayMemoryCell(data.copyOf())

    override fun get(index: IMemoryCell) = when (index) {
        is NumberMemoryCell -> {
            val indexAsInt = index.value.toInt()
            if (indexAsInt < 0 || indexAsInt >= data.size) throw UnknownOperationException("Array index $indexAsInt out of bounds")
            data[indexAsInt]!!
        }
        else -> throw UnknownOperationException("The index based get operation in array is not supported for $index")
    }

    override fun unary(op: UnaryOperation) = when (op) {
        UnaryOperation.BIT_COMPLEMENT -> NumberMemoryCell(BigDecimal.valueOf(data.size.toLong()))
        else -> throw UnknownOperationException("The operation $op is not supported for array data type")
    }

    override fun binary(right: IMemoryCell, op: BinaryOperation) = when (op) {
        BinaryOperation.PLUS -> binaryPlus(right)
        BinaryOperation.EQUAL -> binaryEqual(right)
        BinaryOperation.NOT_EQUAL -> binaryNotEqual(right)
        else -> throw UnknownOperationException("The operation $op is not supported for array data type")
    }

    /**
     * Performs the '+' operations.
     */
    private fun binaryPlus(right: IMemoryCell) = arrayMemoryCell(data + right.copy())

    /**
     * Performs the '==' operation.
     */
    private fun binaryEqual(right: IMemoryCell) = when (right) {
        is ArrayMemoryCell -> boolMemoryCell(data.contentEquals(right.data))
        else -> throw UnknownOperationException("The == operation in array is not supported for $right")
    }

    /**
     * Performs the '!=' operation.
     */
    private fun binaryNotEqual(right: IMemoryCell) = when (right) {
        is ArrayMemoryCell -> boolMemoryCell(!data.contentEquals(right.data))
        else -> throw UnknownOperationException("The != operation in array is not supported for $right")
    }

    override fun stringify() = "[" + data.map { it?.stringify() }.joinToString(", ") + "]"
    override fun toString() = "ArrayMemoryCell(value=[${stringify()}])"
}